package travelplanner.project.demo.global.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageUtil<T> {
    private List<T> content;
    private int currentPageNumber;
    private int totalPageNumber;

    public PageUtil(Page<T> page) {
        this.content = page.getContent();
        this.currentPageNumber = page.getNumber();
        this.totalPageNumber = page.getTotalPages();
    }
}
