package travelplanner.project.demo.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Schema(description = "상태 코드 / 에러 코드 / 에러 문구 DTO")
@Getter
@ToString
public class ApiException{

    @Schema(description = "상태 코드")
    private HttpStatus status;

    @Schema(description = "에러 메세지")
    private String message;

    @Schema(description = "에러 코드")
    private String errorCode;

    @Builder
    public ApiException (HttpStatus status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

}
