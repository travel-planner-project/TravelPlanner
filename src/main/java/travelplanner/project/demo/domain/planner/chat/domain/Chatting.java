package travelplanner.project.demo.domain.planner.chat.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String userNickname;

    private String profileImgUrl;

    private String message;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    @JsonIgnore
    private Planner planner;

    // 연관 관계 편의 메서드
    public void mappingPlanner(Planner planner) {
        this.planner = planner;
        planner.mappingChatting(this);
    }

}
