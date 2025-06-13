package madebyzino.HealthyGram.common.exception;

public enum ErrorCode {

    // 비지니스 로직 에러

    // 회원가입 및 로그인
    EMAIL_DUPLICATE("E001", "이미 사용 중인 이메일입니다."),
    EMAIL_INVALID_FORMAT("E002", "이메일 형식이 올바르지 않습니다."),
    PASSWORD_MISMATCH("E003", "비밀번호가 일치하지 않습니다."),
    NICKNAME_DUPLICATE("E004", "이미 사용중인 닉네임입니다."),



    INVALID_USER("E020", "존재하지 않는 유저입니다."),



    // 서버 내부 에러
    SERVER_INTERNAL_ERROR("500", "서버 내부에 오류가 있습니다."),

    //파일 업로드
    S3_UPLOADER_ERROR("E501", "AWS S3처리 중 오류가 발생하였습니다.");


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
