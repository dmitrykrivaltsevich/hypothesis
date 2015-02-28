package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.container.CartesianProductStream;
import com.conjuncte.hypothesis.container.Pair;
import com.conjuncte.hypothesis.container.Stream;
import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.Hypothesis;
import com.conjuncte.hypothesis.domain.LocalRepository;
import com.conjuncte.hypothesis.domain.Register;
import com.conjuncte.hypothesis.domain.Repository;

import java.io.PrintStream;
import java.math.BigInteger;

// todo: add auto storing
// todo: make better package structure
// todo: apply multi-threading
// todo: use arrays of cells inside register and immutable cell prototypes - will improve memory consumption
public class App {

    private Repository<Hypothesis> hypothesisRepository = new LocalRepository();
    private Pair<Register, Register> latestFactors;

    public static void main(String[] args) {
        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("901746392047", 10).multiply(new BigInteger("873610947299", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("41893284683", 10).multiply(new BigInteger("31893284777", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("1965478943", 10).multiply(new BigInteger("6985321517", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("122949829", 10).multiply(new BigInteger("122951513", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("12231257", 10).multiply(new BigInteger("12413887", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("1182787", 10).multiply(new BigInteger("1571663", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("124351", 10).multiply(new BigInteger("146383", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(11903 * 11587), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(4721 * 2129), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(523 * 541), 10);
        System.out.println(String.format("Going to factor: %s (%d bits)",
                targetProduct.toString(),
                new BigInteger(targetProduct.toString()).bitLength()));

        App application = new App();
        StateMonitor monitor = new QueueStateMonitor(application, 10, System.out).start();

        Pair<Register, Register> factors = application.checkAllHypothesises(targetProduct);
        if (factors != null) {
            System.out.println(String.format("Found factors for %s (%d bits):\n%s\n%s",
                    targetProduct,
                    new BigInteger(targetProduct.toString()).bitLength(),
                    factors.getFirst(),
                    factors.getSecond()));
        } else {
            System.out.println("Factors not found.");
        }
        monitor.stop();
    }

    public Pair<Register, Register> checkAllHypothesises(Register targetProduct) {
        while (!hypothesisRepository.isEmpty()) {
            Hypothesis hypothesis = hypothesisRepository.get();

            latestFactors = hypothesis.getFactors();
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

    public int getNumberOfHypothesisesToCheck() {
        return hypothesisRepository.size();
    }

    public Pair<Register, Register> getLatestFactors() {
        return latestFactors;
    }

    private interface StateMonitor {
        StateMonitor start();

        void stop();
    }

    private static class QueueStateMonitor
            implements StateMonitor {

        private final int MILLISECONDS_IN_SECOND = 1000;

        private final App app;
        private final int sleepInSeconds;
        private final PrintStream out;

        private final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isInterrupted = false;
                while (!isInterrupted) {
                    try {
                        Thread.sleep(sleepInSeconds * MILLISECONDS_IN_SECOND);
                        out.println(String.format(
                                "Hypothesises to check: %d. Current: (%s, %s)",
                                app.getNumberOfHypothesisesToCheck(),
                                app.getLatestFactors().getFirst(),
                                app.getLatestFactors().getSecond()));
                    } catch (InterruptedException e) {
                        isInterrupted = true;
                        System.out.println("Monitor is stopped.");
                    }
                }
            }
        });

        QueueStateMonitor(App app, int sleepInSeconds, PrintStream out) {
            assert app != null;
            assert sleepInSeconds > 0;
            assert out != null;

            this.app = app;
            this.sleepInSeconds = sleepInSeconds;
            this.out = out;
        }

        @Override
        public StateMonitor start() {
            thread.start();
            System.out.println("Monitor started.");
            return this;
        }

        @Override
        public void stop() {
            System.out.println("Going to stop monitor.");
            thread.interrupt();
        }
    }
}
