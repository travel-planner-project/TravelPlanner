package travelplanner.project.demo.member.profile;

import lombok.Getter;

@Getter
public class ProfileEditor {

    private String profileImgUrl;
    private String keyName;

    public ProfileEditor(String profileImgUrl, String keyName) {
        this.profileImgUrl = profileImgUrl;
        this.keyName = keyName;
    }

    public static ProfileEditorBuilder builder() {
        return new ProfileEditorBuilder();
    }

    public static class ProfileEditorBuilder {
        private String profileImgUrl;
        private String keyName;

        ProfileEditorBuilder() {
        }

        public ProfileEditorBuilder profileImgUrl(final String profileImgUrl) {
            if (profileImgUrl != null && !profileImgUrl.isEmpty()) {
                this.profileImgUrl = profileImgUrl;
            }
            return this;
        }

        public ProfileEditorBuilder keyName(final String keyName) {
            if (keyName != null && !keyName.isEmpty()) {
                this.keyName = keyName;
            }
            return this;
        }

        public ProfileEditor build() {
            return new ProfileEditor(profileImgUrl, keyName);
        }
    }
}
