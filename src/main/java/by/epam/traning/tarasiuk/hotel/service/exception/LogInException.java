package by.epam.traning.tarasiuk.hotel.service.exception;

public class LogInException extends Exception{
    public LogInException() {
        super();
    }

    public LogInException(String message) {
        super(message);
    }

    public LogInException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogInException(Throwable cause) {
        super(cause);
    }

    protected LogInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
