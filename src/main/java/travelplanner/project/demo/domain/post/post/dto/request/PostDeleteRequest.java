package travelplanner.project.demo.domain.post.post.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "포스트 삭제 요청 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDeleteRequest {

    @Schema(description = "포스트 인덱스", example = "1")
    private Long postId;
}
