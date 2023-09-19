package travelplanner.project.demo.domain.post.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.post.image.domain.Image;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "포스트 리스트 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponse {

    @Schema(description = "포스트 인덱스", example = "1")
    private Long postId;

    @Schema(description = "포스트 제목", example = "포스트 제목입니다.")
    private String postTitle;

    @Schema(description = "포스트 생성일", example = "9/19")
    private LocalDateTime createdAt;

    @Schema(description = "포스트 썸네일")
    private List<Image> images;

}
