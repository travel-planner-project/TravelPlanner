package travelplanner.project.demo.domain.comment.domain;

import lombok.Getter;

@Getter
public class CommentEditor {

    private String commentContent;

    public CommentEditor(String commentContent) {
        this.commentContent = commentContent;
    }

    public static CommentEditorBuilder builder() {
        return new CommentEditorBuilder();
    }

    public static class CommentEditorBuilder{
        private String commentContent;

        CommentEditorBuilder() {
        }

        public CommentEditorBuilder commentContent(final String commentContent) {
            if (commentContent != null && !commentContent.isEmpty()) {
                this.commentContent = commentContent;
            }
            return this;
        }

        public CommentEditor build() {
            return new CommentEditor(commentContent);
        }
    }

    public String toString() {
        return "CommentEditor.CommentEditorBuilder(commentContent=" + this.commentContent + ")";
    }

}
