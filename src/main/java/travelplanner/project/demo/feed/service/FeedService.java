package travelplanner.project.demo.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.feed.dto.FeedResponse;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.PageUtil;
import travelplanner.project.demo.planner.domain.GroupMember;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.PlannerRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final AuthUtil authUtil;
    private final PlannerRepository plannerRepository;
    private final GroupMemberRepository groupMemberRepository;

    public PageUtil<FeedResponse> getFeedList(String planTitle, Pageable pageable) {
        String currentEmail = authUtil.getCurrentMember().getEmail();
        List<GroupMember> groupMembers = groupMemberRepository.findByEmail(currentEmail);


        Page<Planner> page;

        if (planTitle == null) {
            // 전체 리스트를 가져오는 로직
            page = plannerRepository.findAll(pageable);
        } else {
            // 제목으로 필터링된 결과를 가져오는 로직
            page = plannerRepository.findByIsPrivateFalseAndPlanTitleContaining(planTitle, pageable);
        }

        // 현재 사용자가 그룹 멤버가 아닌 경우 isPrivate이 true인 플래너를 제거
        List<Planner> planners = page.getContent().stream()
                .filter(planner -> !planner.getIsPrivate() || groupMembers.stream().anyMatch(gm -> gm.getPlanner().equals(planner)))
                .toList();

        // Planner 객체를 FeedResponse 객체로 변환
        List<FeedResponse> feedResponses = planners
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
