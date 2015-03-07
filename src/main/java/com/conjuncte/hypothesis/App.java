package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.container.CartesianProductStream;
import com.conjuncte.hypothesis.container.InitialHypothesisStream;
import com.conjuncte.hypothesis.container.Pair;
import com.conjuncte.hypothesis.container.Stream;
import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.Hypothesis;
import com.conjuncte.hypothesis.domain.LocalRepository;
import com.conjuncte.hypothesis.domain.Register;
import com.conjuncte.hypothesis.domain.Repository;
import com.conjuncte.hypothesis.monitor.QueueStateMonitor;
import com.conjuncte.hypothesis.monitor.StateMonitor;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// todo: add auto storing
// todo: make better package structure
// todo: use arrays of cells inside register and immutable cell prototypes - will improve memory consumption
// todo: sharing tasks between factor calculators (when one is done it can crack on into someone else hypot. repo)
public class App {

    // todo: make it configurable, extract from the App
    private static final ExecutorService pool = Executors.newFixedThreadPool(6);
    // todo: extract or remove that, but preserve useful information
    private Pair<Register, Register> latestFactors;

    public static void main(String[] args) {
        final Register targetProduct = new FreeRadixRegister(new BigInteger("901746392047", 10).multiply(new BigInteger("873610947299", 10)).toString(), 10);
//        final Register targetProduct = new FreeRadixRegister(new BigInteger("1182787", 10).multiply(new BigInteger("1571663", 10)).toString(), 10);
        System.out.println(String.format("Going to factor: %s (%d bits)",
                targetProduct.toString(),
                new BigInteger(targetProduct.toString()).bitLength()));

//        App application = new App();
//        StateMonitor monitor = new QueueStateMonitor(application, 10, System.out).start();
//
//        Pair<Register, Register> factors = application.checkAllHypothesises(
//                new SynchronizedRepository<>(new LocalRepository()),
//                targetProduct);
//
//        if (factors != null) {
//            System.out.println(String.format("Found factors for %s (%d bits):\n%s\n%s",
//                    targetProduct,
//                    new BigInteger(targetProduct.toString()).bitLength(),
//                    factors.getFirst(),
//                    factors.getSecond()));
//        } else {
//            System.out.println("Factors not found.");
//        }
//        monitor.stop();

        final App app = new App();
        StateMonitor monitor = new QueueStateMonitor(app, 10, System.out).start();

        CalculationStateCallback stateCallback = new CalculationStateCallback() {
            @Override
            public void onSuccess(@Nonnull Pair<Register, Register> factors) {
                System.out.println(String.format("Found factors for %s (%d bits):\n%s\n%s",
                        targetProduct,
                        new BigInteger(targetProduct.toString()).bitLength(),
                        factors.getFirst(),
                        factors.getSecond()));
                pool.shutdownNow(); // todo: check that all thread will be interrupted correctly
            }

            @Override
            public void onProgress(@Nonnull Pair<Register, Register> factors) {
                app.setLatestFactors(factors);
            }

            @Override
            public void onEnd() {
                // todo: do I need that?
            }
        };


        pool.execute(new FactorsCalculator(
                new LocalRepository(new InitialHypothesisStream(new int[]{1, 3})),
                targetProduct,
                stateCallback
        ));
        pool.execute(new FactorsCalculator(
                new LocalRepository(new InitialHypothesisStream(new int[]{1, 7})),
                targetProduct,
                stateCallback
        ));
        pool.execute(new FactorsCalculator(
                new LocalRepository(new InitialHypothesisStream(new int[]{1, 9})),
                targetProduct,
                stateCallback
        ));
        pool.execute(new FactorsCalculator(
                new LocalRepository(new InitialHypothesisStream(new int[]{3, 7})),
                targetProduct,
                stateCallback
        ));
        pool.execute(new FactorsCalculator(
                new LocalRepository(new InitialHypothesisStream(new int[]{3, 9})),
                targetProduct,
                stateCallback
        ));
        pool.execute(new FactorsCalculator(
                new LocalRepository(new InitialHypothesisStream(new int[]{7, 9})),
                targetProduct,
                stateCallback
        ));
        pool.shutdown();

        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
        monitor.stop();
        System.out.println("Bye...");
    }

    public int getNumberOfHypothesisesToCheck() {
//        return hypothesisRepository.size();
// todo: provide correct value
        return 0;
    }

    public Pair<Register, Register> getLatestFactors() {
        return latestFactors;
    }

    public void setLatestFactors(Pair<Register, Register> latestFactors) {
        this.latestFactors = latestFactors;
    }

    public static interface CalculationStateCallback {
        void onSuccess(@Nonnull Pair<Register, Register> factors);

        void onProgress(@Nonnull Pair<Register, Register> factors);

        void onEnd();
    }

    public static class FactorsCalculator
            implements Runnable {

        private final Repository<Hypothesis> hypothesis;
        private final Register targetProduct;
        private final CalculationStateCallback callback;

        public FactorsCalculator(
                @Nonnull Repository<Hypothesis> hypothesis,
                @Nonnull Register targetProduct,
                @Nonnull CalculationStateCallback callback) {
            assert hypothesis != null;
            assert targetProduct != null;
            assert callback != null;

            this.hypothesis = hypothesis;
            this.targetProduct = targetProduct;
            this.callback = callback;
        }

        @Override
        public void run() {
            Pair<Register, Register> factors = checkAllHypothesises(hypothesis, targetProduct);
            if (factors != null) {
                callback.onSuccess(factors);
            }
            callback.onEnd();
        }

        private Pair<Register, Register> checkAllHypothesises(
                @Nonnull Repository<Hypothesis> hypothesisRepository,
                @Nonnull Register targetProduct) {
            while (!hypothesisRepository.isEmpty() && !Thread.interrupted()) {
                Hypothesis hypothesis = hypothesisRepository.get();

                callback.onProgress(hypothesis.getFactors());
                Pair<Register, Register> factors = hypothesis.getFactors();
                Register factorsFirst = factors.getFirst();
                Register factorsSecond = factors.getSecond();
                Integer cellOffsetToCheck = hypothesis.getCellOffsetToCheck();

                Register partialProduct = factorsFirst.partialProduct(factorsSecond, cellOffsetToCheck);

                if (targetProduct.getCell(cellOffsetToCheck).equals(partialProduct.getCell(cellOffsetToCheck))) {
                    if (targetProduct.getCapacity().equals(partialProduct.getCapacity())
                            && targetProduct.equals(partialProduct)) {
                        return factors;
                    } else if (factorsFirst.getCapacity() + factorsSecond.getCapacity() < targetProduct.getCapacity()) {
                        Stream<Hypothesis> derivedHypothesisesStream = new CartesianProductStream(
                                factors,
                                cellOffsetToCheck + 1);
                        hypothesisRepository.add(derivedHypothesisesStream);
                    }
                }
            }

            // todo: add guava optional
            return null;
        }
    }
}
