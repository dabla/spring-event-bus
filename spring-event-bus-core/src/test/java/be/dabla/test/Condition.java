package be.dabla.test;

import java.util.concurrent.TimeoutException;

public abstract class Condition {
    public abstract boolean validate();

    public TimeoutException exceptionToThrowAfterTimeout() {
        return new TimeoutException();
    }
}
