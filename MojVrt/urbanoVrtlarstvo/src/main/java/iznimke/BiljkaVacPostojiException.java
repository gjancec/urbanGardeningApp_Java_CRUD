package iznimke;

/**
 * Klasa za Custom checked exception
 */
public class BiljkaVacPostojiException extends Exception{
    public BiljkaVacPostojiException() {
    }

    /**
     * Metoda potrebna kada želimo dodati poruku kada bacamo iznimku npr.
     * throw new CustomUncheckedException("Custom Unchecked Exception ");
     */
    public BiljkaVacPostojiException(String message) {
        super(message);
    }

    /**
     * Metoda potrebna kada želimo iznimku staviti u catch blok i ponovo je baciti
     npr catch(ArrayIndexOutOfBoundsException e) {
     * throw new CustomUncheckedException(e);
     */
    public BiljkaVacPostojiException(Throwable cause) {
        super(cause);
    }

    /**
     * Metoda potrebna kada želimo kombinaciju dviju prethodnih metoda npr.
     * catch(ArrayIndexOutOfBoundsException e) {
     * throw new CustomUncheckedException(e, "File not found");
     */
    public BiljkaVacPostojiException(String message, Throwable cause) {
        super(message, cause);
    }


    public BiljkaVacPostojiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
