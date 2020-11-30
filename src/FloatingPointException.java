/**
 * Exception to be thrown when a floating point exception occurs.
 */
public class FloatingPointException extends RuntimeException {
    public FloatingPointException(final String errorMessage) {
        super(errorMessage);
    }
}
