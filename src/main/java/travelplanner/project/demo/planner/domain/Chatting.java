package travelplanner.project.demo.planner.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.planner.domain.Planner;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Chatting {

    @Id
    @GeneratedValue
    @Column(name="chat_id")
    private Long id;

    private String userNickname;

    private String profileImgUrl;

    private String message;

    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "chatting")
    private Planner planner;
}
