package uz.pdp.online.exception;

public class KeyRetrievalException extends RuntimeException {
    public KeyRetrievalException(String message) {
        super(message);
    }

    public KeyRetrievalException(String message, Exception e) {
        super(message, e);
    }
}
