package travelplanner.project.demo.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.feed.dto.FeedResponse;
import travelplanner.project.demo.feed.service.FeedService;
import travelplanner.project.demo.global.util.PageUtil;


@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feed")
    public PageUtil<FeedResponse> getFeed(
            @RequestParam(required = false) String planTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size){
        PageUtil<FeedResponse> feedList = feedService.getFeedList(planTitle, PageRequest.of(page, size));
        return feedList;
    }
}
