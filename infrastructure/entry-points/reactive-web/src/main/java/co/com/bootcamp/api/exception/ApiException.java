package co.com.bootcamp.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public ApiException(Throwable throwable, HttpStatus status, String message) {
        super(throwable);
        this.status = status;
        this.message = message;
    }

    public ApiException(HttpStatus status, String message) {
        this(null, status, message);
    }

    public ApiException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}