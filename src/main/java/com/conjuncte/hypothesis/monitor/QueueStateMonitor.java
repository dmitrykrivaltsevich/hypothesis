package com.conjuncte.hypothesis.monitor;

import com.conjuncte.hypothesis.App;

import java.io.PrintStream;

public class QueueStateMonitor
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

    public QueueStateMonitor(App app, int sleepInSeconds, PrintStream out) {
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
