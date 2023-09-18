package travelplanner.project.demo.domain.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import travelplanner.project.demo.domain.member.editor.MemberEditor;
import travelplanner.project.demo.domain.post.post.domain.Post;
import travelplanner.project.demo.domain.profile.domain.Profile;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userNickname;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Planner> planners = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    /*@JoinColumn(name = "profile_id")*/
    private Profile profile;

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public void mappingPlanner(Planner planner) {
        planners.add(planner);
    }

    private String provider;
    private String providerId;

    public void edit(MemberEditor memberEditor) {
        if (memberEditor.getUserNickname() != null) {
            userNickname = memberEditor.getUserNickname();
        }
        if (memberEditor.getPassword() != null) {
            password = memberEditor.getPassword(); // 비밀번호 수정 로직 추가
        }
    }
}
