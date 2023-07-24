package travelplanner.project.demo.planner.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlannerEditor {

    private String planTitle;

    private Boolean isPrivate;

    public PlannerEditor(String planTitle, Boolean isPrivate) {
        this.planTitle = planTitle;
        this.isPrivate = isPrivate;
    }

    public static PlannerEditorBuilder builder() {
        return new PlannerEditorBuilder();
    }

    public static class PlannerEditorBuilder {
        private String planTitle;
        private Boolean isPrivate;

        PlannerEditorBuilder() {
        }

        public PlannerEditorBuilder planTitle(final String planTitle) {
            if (planTitle != null && !planTitle.isEmpty()) {
                this.planTitle = planTitle;
            }
            return this;
        }

        public PlannerEditorBuilder isPrivate(final Boolean isPrivate) {
            if (isPrivate != null) {
                this.isPrivate = isPrivate;
            }
            return this;
        }


        public PlannerEditor build() {
            return new PlannerEditor(planTitle, isPrivate);
        }

        public String toString() {
            return "PlannerEditor.PlannerEditorBuilder(planTitle=" + this.planTitle + ", isPrivate=" + this.isPrivate + ")";
        }
    }
}
