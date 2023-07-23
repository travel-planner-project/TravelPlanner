package travelplanner.project.demo.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Schema(description = "Exception Type (Enum)")
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
    TOKEN_NOT_EXISTS(HttpStatus.UNAUTHORIZED, "USER-005", "토큰 유효기간이 만료되었거나, 유효하지 않은 토큰입니다."),

    // 특정 유저의 프로필을 찾을 수 없는 경우
    USER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE-001", "해당 유저를 찾을 수 없습니다."),

    // 로그인한 유저와 프로필 유저가 같지 않은 경우
    THIS_USER_IS_NOT_SAME_LOGIN_USER(HttpStatus.UNAUTHORIZED, "PROFILE-002", "프로필 유저와 로그인 유저가 같지 않습니다."),

    // 회원정보 변경 시 비밀번호가 일치하지 않은 경우
    CHECK_PASSWORD_AGAIN(HttpStatus.BAD_REQUEST, "PROFILE-003", "비밀번호를 다시한번 확인해주세요"),
  
    // 플래너가 존재하지 않는 경우
    NOT_EXISTS_PLANNER(HttpStatus.BAD_REQUEST, "PLANNER-OO1", "존재 하지 않는 플래너 입니다."),

    // 내부 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-001", "내부 서버 오류로 인해 요청을 처리할 수 없습니다.");

    @Schema(description = "상태코드")
    private final HttpStatus status;

    @Schema(description = "에러코드")
    private final String errorCode;

    @Schema(description = "상태 메세지")
    private final String message;

    ExceptionType(HttpStatus status, String errorCode, String message) {

        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
