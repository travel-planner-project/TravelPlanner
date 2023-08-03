package travelplanner.project.demo.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Schema(description = "Exception Type (Enum)")
@Getter
@ToString
public enum ErrorType {


    // 권한이 부족한 경우
    USER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "AUTH-001", "권한이 부족하여 접근할 수 없습니다."),

    // 비밀번호가 일치하지 않은 경우
    CHECK_PASSWORD_AGAIN(HttpStatus.BAD_REQUEST, "USER-001", "비밀번호를 다시한번 확인해주세요"),

    // 사용자를 찾을 수 없는 경우
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-02", "유저가 존재하지 않습니다."),

    // 내부 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-001", "내부 서버 오류로 인해 요청을 처리할 수 없습니다."),

    // 입력하지 않은 요소가 존재하는 경우
    NULL_VALUE_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-002", "입력하지 않은 요소가 있습니다."),

    // 페이지 찾을 수 없는 경우
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT-001", "페이지를 찾을 수 없습니다."),

    // 유효하지 않은 요청일 경우
    INVALID_REQUEST(HttpStatus.UNPROCESSABLE_ENTITY, "CLIENT-002", "페이지를 찾을 수 없습니다."),

    // ==================================================================================================================


    // 이미 이메일이 존재하는 경우
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "USER-003", "이미 존재하는 이메일 입니다."),

    PLANNER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "PLANNER-001", "권한이 부족하여 접근할 수 없습니다."),

    PLANNER_NOT_FOUND(HttpStatus.NOT_FOUND, "PLANNER-002", "플래너가 존재하지 않습니다."),

    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "PLANNER-003", "투두가 존재하지 않습니다."),

    DATE_NOT_FOUND(HttpStatus.NOT_FOUND, "PLANNER-004", "데이트가 존재하지 않습니다.");
    // ==================================================================================================================


    private final HttpStatus status;
    private final String errorCode;
    private final String message;


    ErrorType(HttpStatus status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
