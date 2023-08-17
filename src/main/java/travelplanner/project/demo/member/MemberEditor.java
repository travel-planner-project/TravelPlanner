package travelplanner.project.demo.member;

import lombok.Getter;

@Getter
public class MemberEditor {
    private String userNickname;

    public MemberEditor(String userNickname) {
        this.userNickname = userNickname;
    }

    public static MemberEditorBuilder builder() {
        return new MemberEditorBuilder();
    }

    public static class MemberEditorBuilder {
        private String userNickname;

        MemberEditorBuilder() {
        }

        public MemberEditorBuilder userNickname(final String userNickname) {
            if (userNickname != null && !userNickname.isEmpty()) {
                this.userNickname = userNickname;
            }
            return this;
        }

        public MemberEditor build() {
            return new MemberEditor(userNickname);
        }
    }
}
