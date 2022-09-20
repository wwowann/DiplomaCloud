package lesson.project.controler;

import lesson.project.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * контроллер обработки ошибок
 */

@RestControllerAdvice
@Slf4j//логирование
public class ErrorController {
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<Error> handleUnauthorizedUser(HttpClientErrorException.Unauthorized e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Error("401 " + e.getMessage()));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Error> handleAppException(AppException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error("400 " + e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error("400 " + e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleAnotherException(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error("500 " + e.getMessage()));
    }
}
