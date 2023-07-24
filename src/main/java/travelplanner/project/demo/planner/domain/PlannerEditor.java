package travelplanner.project.demo.planner.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlannerEditor {

    private String planTitle;

    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


    public PlannerEditor(String planTitle, Boolean isPrivate, LocalDateTime startDate, LocalDateTime endDate) {
        this.planTitle = planTitle;
        this.isPrivate = isPrivate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PlannerEditorBuilder builder() {
        return new PlannerEditorBuilder();
    }

    public static class PlannerEditorBuilder {
        private String planTitle;
        private Boolean isPrivate;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

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

        public PlannerEditorBuilder startDate(final LocalDateTime startDate) {
            if (startDate != null) {
                this.startDate = startDate;
            }
            return this;
        }

        public PlannerEditorBuilder endDate(final LocalDateTime endDate) {
            if (endDate != null) {
                this.endDate = endDate;
            }
            return this;
        }

        public PlannerEditor build() {
            return new PlannerEditor(planTitle, isPrivate, startDate, endDate);
        }

        public String toString() {
            return "PlannerEditor.PlannerEditorBuilder(planTitle=" + this.planTitle + ", isPrivate=" + this.isPrivate +
                    ", startDate=" + this.startDate + ", endDate=" + this.endDate + ")";
        }
    }
}
