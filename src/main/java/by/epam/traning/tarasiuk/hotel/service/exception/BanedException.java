package by.epam.traning.tarasiuk.hotel.service.exception;

public class BanedException extends Exception {
    public BanedException() {
        super();
    }

    public BanedException(String message) {
        super(message);
    }

    public BanedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BanedException(Throwable cause) {
        super(cause);
    }

    protected BanedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
