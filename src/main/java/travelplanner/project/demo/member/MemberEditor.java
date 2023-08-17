package travelplanner.project.demo.member;

import lombok.Getter;

@Getter
public class MemberEditor {
    private String userNickname;
    private String password; // 비밀번호 필드 추가

    public MemberEditor(String userNickname, String password) {
        this.userNickname = userNickname;
        this.password = password;
    }

    public static MemberEditorBuilder builder() {
        return new MemberEditorBuilder();
    }

    public static class MemberEditorBuilder {
        private String userNickname;
        private String password;

        MemberEditorBuilder() {
        }

        public MemberEditorBuilder userNickname(final String userNickname) {
            if (userNickname != null && !userNickname.isEmpty()) {
                this.userNickname = userNickname;
            }
            return this;
        }

        public MemberEditorBuilder password(final String password) {
            if (password != null && !password.isEmpty()) {
                this.password = password;
            }
            return this;
        }

        public MemberEditor build() {
            return new MemberEditor(userNickname, password);
        }
    }
}
