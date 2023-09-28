package travelplanner.project.demo.domain.post.post.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.comment.domain.Comment;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.planner.calender.domain.Calendar;
import travelplanner.project.demo.domain.post.image.domain.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    private String postTitle;

    private String postContent;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<Image> images = new ArrayList<>();

    //댓글 추가

    @OneToMany(mappedBy = "post",  cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void mappingComment(Comment comment) {
        comments.add(comment);
    }
}
