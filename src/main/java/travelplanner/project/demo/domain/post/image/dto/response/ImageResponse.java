package travelplanner.project.demo.domain.post.image.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "포스트 이미지 상세 응답 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    @Schema(description = "포스트 이미지 인덱스", example = "1")
    private Long imageId;

    @Schema(description = "포스트 이미지 url")
    private String postImaUrl;

}
