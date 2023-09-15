package travelplanner.project.demo.domain.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.feed.dto.FeedResponse;
import travelplanner.project.demo.global.util.PageUtil;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;
import travelplanner.project.demo.domain.planner.planner.repository.PlannerRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final PlannerRepository plannerRepository;

    public PageUtil<FeedResponse> getFeedList(String planTitle, Pageable pageable) {

        Page<Planner> page;

        if (planTitle == null) {
            // 전체 리스트를 가져오는 로직
            page = plannerRepository.findByIsPrivateFalseOrderByIdDesc(pageable);
        } else {
            // 제목으로 필터링된 결과를 가져오는 로직
            page = plannerRepository.findByIsPrivateFalseAndPlanTitleContainingOrderByIdDesc(planTitle, pageable);
        }

        List<FeedResponse> feedResponses = page.getContent()
                .stream()
                .map(planner -> new FeedResponse(
                        planner.getId(),
                        planner.getPlanTitle(),
                        planner.getStartDate(),
                        planner.getEndDate(),
                        planner.getMember().getUserNickname(),
                        planner.getMember().getProfile().getProfileImgUrl()
                ))
                .collect(Collectors.toList());

        // PageUtil 객체 생성
        return new PageUtil<>(feedResponses, pageable, page.getTotalElements());
    }

}
