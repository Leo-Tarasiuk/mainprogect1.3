package by.epam.traning.tarasiuk.hotel.util.exception;

public class CalcPriceException extends Exception {
    public CalcPriceException() {
        super();
    }

    public CalcPriceException(String message) {
        super(message);
    }

    public CalcPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalcPriceException(Throwable cause) {
        super(cause);
    }

    protected CalcPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
