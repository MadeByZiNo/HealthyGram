package madebyzino.HealthyGram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 로직 오류는 400번
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(CustomException ex) {
        ErrorResponse body = new ErrorResponse(ex.getErrorCode(), ex.getDetailMessage());
        return ResponseEntity
                .badRequest()
                .body(body);
    }

    // 서버 내부 에러는 500번
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        ErrorResponse body = new ErrorResponse(
                ErrorCode.SERVER_INTERNAL_ERROR,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

}
