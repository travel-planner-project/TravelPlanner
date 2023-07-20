package travelplanner.project.demo.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiException> exceptionHandler (HttpServletRequest request, final Exception e) {

        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiException.builder()
                        .errorCode(e.getError().getErrorCode())
                        .message(e.getError().getMessage())
                        .build());
    }
}
