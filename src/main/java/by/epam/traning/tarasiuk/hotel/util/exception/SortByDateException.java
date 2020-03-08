package by.epam.traning.tarasiuk.hotel.util.exception;

public class SortByDateException extends Exception {
    public SortByDateException() {
        super();
    }

    public SortByDateException(String message) {
        super(message);
    }

    public SortByDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public SortByDateException(Throwable cause) {
        super(cause);
    }

    protected SortByDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
