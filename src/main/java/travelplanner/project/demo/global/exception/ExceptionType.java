package travelplanner.project.demo.global.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionType {

    // 회원 가입 시, null 값이 들어온 경우
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "USER-001", "회원가입 양식을 다시 한번 확인해 주세요."),

    // 이미 이메일이 존재하는 경우
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "USER-002", "이미 존재하는 이메일 입니다."),

    // 이메일이나 비밀번호가 틀린 경우
    USER_NOT_FOUND(HttpStatus.FORBIDDEN, "USER-003", "로그인 정보를 다시 확인해 주세요."),

    // 권한이 부족한 경우
    USER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "USER-004", "권한이 부족하여 접근할 수 없습니다."),

    // 토큰 유효기간이 끝난 경우
    TOKEN_NOT_EXISTS(HttpStatus.UNAUTHORIZED, "USER-005", "토큰 유효기간이 만료되었거나, 유효하지 않은 토큰입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    ExceptionType(HttpStatus status, String errorCode, String message) {

        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
