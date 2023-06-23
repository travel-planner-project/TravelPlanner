package server.domain.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment {

    @Id
    private Long commentId;

    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    @ManyToOne
    private User user;

    private String commentContent;

    @Column(columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;
}
