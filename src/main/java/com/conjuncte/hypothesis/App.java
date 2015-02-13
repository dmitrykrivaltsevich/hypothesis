package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.domain.*;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Hello world!
 */
public class App {

    // todo: add initial hypothesises
    private Queue<Stream<Hypothesis>> hypothesises = new PriorityQueue<Stream<Hypothesis>>(
            1000000,
            new Comparator<Stream<Hypothesis>>() {
                @Override
                public int compare(Stream<Hypothesis> streamOne, Stream<Hypothesis> streamTwo) {
                    return -streamOne.compareTo(streamTwo);
                }
            }
    ) {{
        add(new InitialHypothesisStream());
    }};


    public static void main(String[] args) {
// todo: highlights bugs with 0s (no factos found)
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(11903 * 11587), 10);

//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("1965478943", 10).multiply(new BigInteger("6985321517", 10)).toString(), 10);
        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("122949829", 10).multiply(new BigInteger("122951513", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("12231257", 10).multiply(new BigInteger("12413887", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("1182787", 10).multiply(new BigInteger("1571663", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("124351", 10).multiply(new BigInteger("146383", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(11777 * 11587), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(4721 * 2129), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(523 * 541), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(11* 11), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(7* 7), 10);
        App application = new App();
        StateMonitor monitor = new QueueStateMonitor(application.getHypothesises(), 10, System.out).start();

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

    private Pair<Register, Register> checkAllHypothesises(Register targetProduct) {
        Stream<Hypothesis> hypothesisStream;
        while ((hypothesisStream = hypothesises.poll()) != null) {
            while (hypothesisStream.hasNext()) {
                Hypothesis hypothesis = hypothesisStream.next();

                Pair<Register, Register> factors = hypothesis.getFactors();
                Register factorsFirst = factors.getFirst();
                Register factorsSecond = factors.getSecond();
                Integer cellOffsetToCheck = hypothesis.getCellOffsetToCheck();

                Register partialProduct = factorsFirst.partialProduct(factorsSecond, cellOffsetToCheck);
                // todo: seems we don't need this extra-check
                if (!partialProduct.hasCell(cellOffsetToCheck)) {
                    continue;
                }

                if (targetProduct.getCell(cellOffsetToCheck).equals(partialProduct.getCell(cellOffsetToCheck))) {
                    if (targetProduct.getCapacity().equals(partialProduct.getCapacity())
                            && targetProduct.equals(partialProduct)) {
                        return factors;
                    } else if (factorsFirst.getCapacity() + factorsSecond.getCapacity() < targetProduct.getCapacity()) {
                        Stream<Hypothesis> derivedHypothesisesStream = new CartesianProductStream(
                                factors,
                                cellOffsetToCheck + 1);
                        hypothesises.add(derivedHypothesisesStream);
                    }
                }
            }
        }

        // todo: add guava optional
        return null;
    }

    public Queue<Stream<Hypothesis>> getHypothesises() {
        return hypothesises;
    }

    private interface StateMonitor {
        StateMonitor start();

        void stop();
    }

    private static class QueueStateMonitor
            implements StateMonitor {

        private final Queue<Stream<Hypothesis>> queue;
        private final int sleepInSeconds;
        private final PrintStream out;

        private final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isInterrupted = false;
                while (!isInterrupted) {
                    try {
                        out.println(String.format("Hypothesises to check: %d", queue.size()));
                        Thread.sleep(sleepInSeconds * 1000);
                    } catch (InterruptedException e) {
                        isInterrupted = true;
                        System.out.println("Monitor is stopped.");
                    }
                }
            }
        });

        QueueStateMonitor(Queue<Stream<Hypothesis>> queue, int sleepInSeconds, PrintStream out) {
            assert queue != null;
            assert sleepInSeconds > 0;
            assert out != null;

            this.queue = queue;
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
