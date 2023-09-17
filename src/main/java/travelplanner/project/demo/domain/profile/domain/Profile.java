package travelplanner.project.demo.domain.profile.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.planner.groupmember.domain.GroupMember;

import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String profileImgUrl;

    private String keyName;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<GroupMember> groupMembers  = new ArrayList<>();
    public ProfileEditor.ProfileEditorBuilder toEditor() {
        return ProfileEditor.builder()
                .profileImgUrl(profileImgUrl)
                .keyName(keyName);
    }

    public void edit(ProfileEditor profileEditor) {
        profileImgUrl = profileEditor.getProfileImgUrl();
        keyName = profileEditor.getKeyName();
    }
}
