package by.epam.traning.tarasiuk.hotel.util.exception;

public class ClientParserException extends Exception {
    public ClientParserException() {
        super();
    }

    public ClientParserException(String message) {
        super(message);
    }

    public ClientParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientParserException(Throwable cause) {
        super(cause);
    }

    protected ClientParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
