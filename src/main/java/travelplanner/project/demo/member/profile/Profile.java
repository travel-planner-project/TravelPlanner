package travelplanner.project.demo.member.profile;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import travelplanner.project.demo.member.Member;

@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue
    private Long profileId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Member member;

    private String profileImgUrl;
    private String keyName;
}
