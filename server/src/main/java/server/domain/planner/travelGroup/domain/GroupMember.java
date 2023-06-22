package server.domain.planner.travelGroup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.user.domain.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupMember {

    // 그룹멤버 인덱스
    @Id
    private Long groupMemberId;

    // 그룹멤버
    @ManyToOne
    private User user;

    // 멤버 Role

    /*
        기본을 MEMBER 로 두고 플래너 생성시에
        플래너 생성 유저를 HOST 로 설정하기.
     */

    @ColumnDefault("MEMBER")
    private String memberRole;
}
