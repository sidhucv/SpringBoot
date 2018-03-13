package springframework.exception;

public class NumberException extends NumberFormatException {
    public NumberException() {
    }

    public NumberException(String s) {
        super(s);
    }
}
