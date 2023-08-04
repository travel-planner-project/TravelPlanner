package travelplanner.project.demo.chat.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Chatting {

    @Id
    @GeneratedValue
    @Column(name="chat_id")
    private Long id;

    private String From;

    private String to;

    private String message;

    private LocalDateTime createdAt;
}
