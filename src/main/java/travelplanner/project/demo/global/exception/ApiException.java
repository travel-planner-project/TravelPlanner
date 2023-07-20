package travelplanner.project.demo.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Schema(description = "에러 코드 / 에러 문구 DTO")
@Getter
@ToString
public class ApiException{

    @Schema(description = "에러 문구", example = "회원가입 양식을 다시 한번 확인해 주세요.")
    private String message;

    @Schema(description = "에러 코드", example = "USER-001")
    private String errorCode;

    @Builder
    public ApiException (HttpStatus status, String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
