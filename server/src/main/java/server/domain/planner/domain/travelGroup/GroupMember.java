package server.domain.planner.domain.travelGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.user.domain.User;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GroupMember {

    // 그룹멤버 인덱스
    @Id
    @GeneratedValue
    private Long groupMemberId;

    // 그룹멤버
    @ManyToOne
    private User user;

    Long userId;

    String userNickname;

    // 멤버 Role
    @Enumerated(EnumType.STRING)
    private GroupMemberType groupMemberType;
}