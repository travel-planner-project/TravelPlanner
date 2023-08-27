package travelplanner.project.demo.member.profile;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.planner.domain.GroupMember;
import travelplanner.project.demo.planner.domain.Planner;

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

    @OneToMany(mappedBy = "profile")
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
