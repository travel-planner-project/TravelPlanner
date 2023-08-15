package travelplanner.project.demo.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.feed.dto.FeedResponse;
import travelplanner.project.demo.global.util.PageUtil;
import travelplanner.project.demo.planner.repository.PlannerRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final PlannerRepository plannerRepository;

    public PageUtil<FeedResponse> getFeedList(String planTitle, int page, int size) {


        return null;
    }

}
