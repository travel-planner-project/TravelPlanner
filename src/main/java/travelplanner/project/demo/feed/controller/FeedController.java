package travelplanner.project.demo.feed.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.feed.dto.FeedResponse;
import travelplanner.project.demo.feed.service.FeedService;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;
import travelplanner.project.demo.global.util.PageUtil;


@RestController
@RequiredArgsConstructor
@Tag(name = "Feed", description = "피드 API")
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "피드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "피드 조회 성공",
            content = @Content(schema = @Schema(implementation = FeedResponse.class)))
    })
    @GetMapping("/feed")
    public PageUtil<FeedResponse> getFeed(
            @Parameter(name = "planTitle", description = "플래너 제목", in = ParameterIn.QUERY) // swagger
            @RequestParam(required = false) String planTitle,

            @Parameter(name = "page", description = "몇번째 페이지(0부터 시작), 기본값 0", in = ParameterIn.QUERY) // swagger
            @RequestParam(defaultValue = "0") int page,

            @Parameter(name = "size", description = "희망 플래너 갯수, 기본값 6", in = ParameterIn.QUERY) // swagger
            @RequestParam(defaultValue = "6") int size) {
        PageUtil<FeedResponse> feedList = feedService.getFeedList(planTitle, PageRequest.of(page, size));
        return feedList;
    }
}
