package travelplanner.project.demo.planner.domain;

import lombok.Getter;

@Getter
public class CalendarEditor {

    private String dateTitle;

    public CalendarEditor(String dateTitle) {
        this.dateTitle = dateTitle;
    }

    public static CalendarEditorBuilder builder() {
        return new CalendarEditorBuilder();
    }

    public static class CalendarEditorBuilder {
        private String dateTitle;

        CalendarEditorBuilder() {
        }

        public CalendarEditorBuilder dateTitle(final String dateTitle) {
            if (dateTitle != null && !dateTitle.isEmpty()) {
                this.dateTitle = dateTitle;
            }
            return this;
        }

        public CalendarEditor build() {
            return new CalendarEditor(dateTitle);
        }

        public String toString() {
            return "CalendarEditor.CalendarEditorBuilder(dateTitle=" + this.dateTitle + ")";
        }
    }
}
