package by.epam.traning.tarasiuk.hotel.util.exception;

public class DateParserException extends Exception {
    public DateParserException() {
        super();
    }

    public DateParserException(String message) {
        super(message);
    }

    public DateParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateParserException(Throwable cause) {
        super(cause);
    }

    protected DateParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
