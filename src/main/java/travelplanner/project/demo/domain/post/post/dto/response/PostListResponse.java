package travelplanner.project.demo.domain.post.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.post.image.domain.Image;

import java.util.List;

@Schema(description = "포스트 리스트 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponse {

    @Schema(description = "포스트 인덱스", example = "1")
    private Long postId;

    @Schema(description = "포스트 썸네일 리스트", example = "")
    private List<Image> images;




}
