package travelplanner.project.demo.domain.post.post.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.post.image.domain.Image;

import java.util.List;

@Schema(description = "포스트 생성 요청 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {

    @Schema(description = "포스트 타이틀", example = "제목")
    private String postTitle;

    @Schema(description = "포스트 내용", example = "내용")
    private String postContent;

}
