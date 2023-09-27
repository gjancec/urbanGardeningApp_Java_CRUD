package iznimke;


/**
 * Klasa za Neoznačenu iznimka (IllegalArgumentException) baca se kada je unesen krivi format podatka
 */
public class KriviFormatException extends RuntimeException{
    public KriviFormatException() {
    }

    public KriviFormatException(String message) {
        super(message);
    }

    public KriviFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public KriviFormatException(Throwable cause) {
        super(cause);
    }

    public KriviFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
