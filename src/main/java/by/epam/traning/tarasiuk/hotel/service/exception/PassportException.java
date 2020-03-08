package by.epam.traning.tarasiuk.hotel.service.exception;

public class PassportException extends Exception{
    public PassportException() {
        super();
    }

    public PassportException(String message) {
        super(message);
    }

    public PassportException(String message, Throwable cause) {
        super(message, cause);
    }

    public PassportException(Throwable cause) {
        super(cause);
    }

    protected PassportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
