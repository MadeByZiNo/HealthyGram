package madebyzino.HealthyGram.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String code;
    private String message;
    private String detailMessage;

    public ErrorResponse(ErrorCode errorCode, String detailMessage) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.detailMessage = detailMessage;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this(errorCode, null);  // 기본은 detailMessage 없이
    }

}
