package rs.ac.bg.fon.airbnb_backend.exception;

public class JdbcException extends RuntimeException {

    public JdbcException(Throwable cause) {
        super(cause);
    }

    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }
}
