package travelplanner.project.demo.domain.comment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;
import travelplanner.project.demo.domain.post.post.domain.Post;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void mappingPost(Post post) {
        this.post = post;
        post.mappingComment(this);
    }
}
