package be.dabla.test;

import java.util.concurrent.TimeoutException;

public class Poller {

    private static final int POLLING_INTERVAL_MS = 50;
    private static final int TIMEOUT_MS = 90000;

    private int pollingInterval = POLLING_INTERVAL_MS;
    private int timeout = TIMEOUT_MS;
    private Condition condition;

    private Poller() {
    }

    public static Poller aPoller() {
        return new Poller();
    }

    public void doAssert(Assertion assertion) throws TimeoutException {
        withCondition(assertion.getCondition()).poll();
    }

    public void run(Runnable runnable) throws TimeoutException {
        withCondition(new DefaultCondition(runnable)).poll();
    }

    public Poller withCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public Poller withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public Poller withPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public void poll() throws TimeoutException {
        long startTime = System.currentTimeMillis();
        while (!condition.validate() && getDurationSince(startTime) < timeout) {
            Sleeper.sleep(pollingInterval);
        }
        if (getDurationSince(startTime) >= timeout) {
            throw condition.exceptionToThrowAfterTimeout();
        }
    }

    private long getDurationSince(long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}
