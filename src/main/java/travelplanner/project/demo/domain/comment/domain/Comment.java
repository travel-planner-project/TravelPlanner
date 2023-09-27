package travelplanner.project.demo.domain.comment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;
import travelplanner.project.demo.domain.post.post.domain.Post;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentContent;

//    private Long parentId;

//    private String writer;
//    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void mappingPost(Post post) {
        this.post = post;
        post.mappingComment(this);
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 자식 엔터티가 더 이상 부모 엔터티에 참조되지 않을 때, 자식 엔터티를 자동으로 삭제
    // 부모 댓글이 삭제될 때 그에 속한 모든 대댓글도 함께 삭제
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public CommentEditor.CommentEditorBuilder toEditor() {
        return CommentEditor.builder()
                .commentContent(commentContent);
    }

    public void edit(CommentEditor commentEditor) {
        commentContent = commentEditor.getCommentContent();
    }

}
