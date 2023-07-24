package travelplanner.project.demo.planner.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ToDoEditor {

    // 일정 제목
    private String itemTitle;
    // 일정 시간
    private LocalDateTime itemDate;
    // 일정 분류
    private String category;
    // 일정 주소
    private String itemAddress;
    // 지출 금액
    private Long budget;
    // 공개 비공개 여부
    private Boolean isPrivate;
    // 할 일 내용
    private String content;

    public static ToDoEditorBuilder builder() {
        return new ToDoEditorBuilder();
    }

    @NoArgsConstructor
    public static class ToDoEditorBuilder{
        // 일정 제목
        private String itemTitle;
        // 일정 시간
        private LocalDateTime itemDate;
        // 일정 분류
        private String category;
        // 일정 주소
        private String itemAddress;
        // 지출 금액
        private Long budget;
        // 공개 비공개 여부
        private Boolean isPrivate;
        // 할 일 내용
        private String content;

        public ToDoEditorBuilder itemTitle(final String itemTitle) {
            if (itemTitle != null && !itemTitle.isEmpty()) {
                this.itemTitle = itemTitle;
            }
            return this;
        }

        // TODO 날짜 받는 데이터 확인하기
        public ToDoEditorBuilder itemDate(final LocalDateTime itemDate) {
            if (itemDate != null) {
                this.itemDate = itemDate;
            }
            return this;
        }

        public ToDoEditorBuilder category(final String category) {
            if (category != null && !category.isEmpty()) {
                this.category = category;
            }
            return this;
        }

        public ToDoEditorBuilder itemAddress(final String itemAddress) {
            if (itemAddress != null && !itemAddress.isEmpty()) {
                this.itemAddress = itemAddress;
            }
            return this;
        }

        public ToDoEditorBuilder budget(final Long budget) {
            if (budget != null) {
                this.budget = budget;
            }
            return this;
        }

        public ToDoEditorBuilder isPrivate(final Boolean isPrivate) {
            if (isPrivate != null) {
                this.isPrivate = isPrivate;
            }
            return this;
        }

        public ToDoEditorBuilder content(final String content) {
            if (content != null && !content.isEmpty()) {
                this.content = content;
            }
            return this;
        }
    }

}
