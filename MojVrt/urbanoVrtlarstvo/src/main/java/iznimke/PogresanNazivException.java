package iznimke;
/**
 * Klasa za Custom checked exception
 */
public class PogresanNazivException extends Exception{
    public PogresanNazivException() {
    }

    public PogresanNazivException(String message) {
        super(message);
    }

    public PogresanNazivException(String message, Throwable cause) {
        super(message, cause);
    }

    public PogresanNazivException(Throwable cause) {
        super(cause);
    }

    public PogresanNazivException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
