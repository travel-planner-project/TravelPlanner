package travelplanner.project.demo.domain.post.post.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "포스트 수정 요청 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequest {

    @Schema(description = "포스트 인덱스", example = "1")
    private Long postId;
    
    @Schema(description = "포스트 타이틀", example = "제목")
    private String postTitle;

    @Schema(description = "포스트 내용", example = "내용")
    private String postContent;

}
