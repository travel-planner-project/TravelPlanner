package travelplanner.project.demo.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.comment.domain.Comment;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    private String commentContent;
    private Long parentId;
}
