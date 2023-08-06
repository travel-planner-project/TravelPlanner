package travelplanner.project.demo.planner.domain;

import lombok.Getter;

@Getter
public class CalendarEditor {

    private String eachDate;

    public CalendarEditor(String eachDate) {
        this.eachDate = eachDate;
    }

    public static CalendarEditorBuilder builder() {
        return new CalendarEditorBuilder();
    }

    public static class CalendarEditorBuilder {
        private String eachDate;

        CalendarEditorBuilder() {
        }

        public CalendarEditorBuilder eachDate(final String eachDate) {
            if (eachDate != null && !eachDate.isEmpty()) {
                this.eachDate = eachDate;
            }
            return this;
        }

        public CalendarEditor build() {
            return new CalendarEditor(eachDate);
        }

        public String toString() {
            return "CalendarEditor.CalendarEditorBuilder(eachDate=" + this.eachDate + ")";
        }
    }
}
