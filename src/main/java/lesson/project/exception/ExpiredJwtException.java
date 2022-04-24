package lesson.project.exception;

public class ExpiredJwtException extends AppException{
    public ExpiredJwtException() {
        super();
    }

    public ExpiredJwtException(String message) {
        super(message);
    }

    public ExpiredJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredJwtException(Throwable cause) {
        super(cause);
    }
}
