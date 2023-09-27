package iznimke;

/**
 * Klasa za custom Unchecked Exception (NullPointerException)
 */

public class NazivNijeUnesen extends RuntimeException{
    public NazivNijeUnesen() {
    }

    public NazivNijeUnesen(String message) {
        super(message);
    }

    public NazivNijeUnesen(String message, Throwable cause) {
        super(message, cause);
    }

    public NazivNijeUnesen(Throwable cause) {
        super(cause);
    }

    public NazivNijeUnesen(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
