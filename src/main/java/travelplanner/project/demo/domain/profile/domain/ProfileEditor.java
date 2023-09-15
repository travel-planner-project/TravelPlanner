package travelplanner.project.demo.domain.profile.domain;

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
            this.profileImgUrl = profileImgUrl;
            return this;
        }

        public ProfileEditorBuilder keyName(final String keyName) {
            this.keyName = keyName;
            return this;
        }

        public ProfileEditor build() {
            return new ProfileEditor(profileImgUrl, keyName);
        }
    }
}
