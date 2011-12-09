package it.haslearnt.commonExceptions;

/**
 * Thrown when runtime reaches some code that should not be reached no matter what.
 */
@SuppressWarnings("serial")
public class ThingThatShouldNotBeException extends RuntimeException {
    public ThingThatShouldNotBeException() {
    }

    public ThingThatShouldNotBeException(String message) {
        super(message);
    }

    public ThingThatShouldNotBeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThingThatShouldNotBeException(Throwable cause) {
        super(cause);
    }
}