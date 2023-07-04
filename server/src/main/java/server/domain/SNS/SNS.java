package server.domain.SNS;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.comment.domain.Comment;
import server.domain.user.domain.User;
import server.global.util.file.File;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SNS {

    // post 인덱스
    @Id
    @GeneratedValue
    private Long postId;

    // post 작성자
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    // post 제목
    private String postTitle;

    // post 사진
//    @OneToMany(cascade = CascadeType.ALL)
//    @Builder.Default
//    private List<File> files = new ArrayList<>();

    // post 내용
    private String postContent;

    // post 작성 시간
    @Column(columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    // post 댓글
    @OneToMany(cascade = CascadeType.ALL)    
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}
