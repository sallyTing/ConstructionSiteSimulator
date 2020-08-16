package org.example.constructionsitesimulator;

public class TerminateException extends Exception {
    private final Simulator simulator;
    private final Exception reason;

    public TerminateException(final Simulator simulator, final Exception reason) {
        this.simulator = simulator;
        this.reason = reason;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public Exception getReason() {
        return reason;
    }
}
