package server.domain.planner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.planner.domain.Planner;
import server.domain.planner.domain.travelGroup.GroupMember;
import server.domain.planner.domain.travelGroup.TravelGroup;
import server.domain.planner.dto.request.GroupMemberUpdateRequest;
import server.domain.planner.dto.request.PlannerCreateRequest;
import server.domain.planner.dto.request.PlannerUpdateRequest;
import server.domain.planner.dto.request.UserSearchRequest;
import server.domain.planner.dto.response.PlannerDetailResponse;
import server.domain.planner.repository.GroupMemerRepository;
import server.domain.planner.repository.PlannerRepository;
import server.domain.planner.repository.TravelGroupRepository;
import server.domain.user.domain.User;
import server.domain.user.repository.UserRepository;
import server.global.code.ErrorCode;
import server.global.exception.HandlableException;
import server.global.security.jwt.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;
    private final GroupMemerRepository groupMemberRepository;
    private final TravelGroupRepository travelGroupRepository;


    // 플래너 리스트
    @Transactional(readOnly = true)
    public Page<Planner> findPlannerListByUserId (Long userId, Pageable pageable) {
        return plannerRepository.findByUserId(userId, pageable);
    }

    // 플래너 생성
    @Transactional
    public void createPlanner (PlannerCreateRequest request) {

        User user =  userRepository.findByUserId(request.getUserId()).get();

        GroupMember groupMember = GroupMember.builder()
                .user(user)
                .groupMemberType(request.getGroupMemberType())
                .build();

        groupMemberRepository.save(groupMember);

        TravelGroup travelGroup = new TravelGroup();
        travelGroup.addMembers(groupMember);
        travelGroupRepository.saveAndFlush(travelGroup);

        Planner planner = Planner.createPlanner(request, travelGroup);

        plannerRepository.saveAndFlush(planner);
    }

    // 플래너 제거
    @Transactional
    public void deletePlanner (Long plannerId) {

        // 제거할 플래너 찾기
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        // 현재 사용자 정보
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = ((UserDetailsImpl) principal).getUser().getUserId();

        // 플래너를 만든 사람이 내가 아니면, 플래너의 그룹에서 '나' 를 제거해야 한다.
        if (!userId.equals(planner.getUserId())) {

            TravelGroup travelGroup = travelGroupRepository.findById(planner.getTravelGroup().getTravelGroupId())
                    .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_GROUP_MEMBER));

            List<GroupMember> groupMembers = travelGroup.getGroupMembers();

            for(GroupMember groupMember: groupMembers) {

                if (groupMember.getUser().getUserId().equals(((UserDetailsImpl) principal).getUser().getUserId())) {
                    travelGroup.removeMember(groupMember);
                    break;
                }
            }

            travelGroupRepository.save(travelGroup);

        } else {

            // 플래너를 만든 사람이 '나' 이면 플래너를 아예 삭제한다.
            plannerRepository.delete(planner);
        }
    }

    // 플래너 세부내용
    public PlannerDetailResponse findPlannerByPlannerId (Long plannerId) {

        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        return new PlannerDetailResponse(planner);
    }

    // 플래너 변경
    @Transactional
    public void updatePlanner(PlannerUpdateRequest dto) {

        Planner planner = plannerRepository.findById(dto.getPlannerId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        planner.updatePlanner(dto);

        System.out.println("플래너 제목: " + dto.getPlanTitle());
        System.out.println("플래너 공개여부: " + dto.getIsPrivate());

        plannerRepository.flush();
    }

    // 플래너 그룹에 멤버 추가
    @Transactional
    public User addGroupMember (GroupMemberUpdateRequest request, Long plannerId) {

        User user = userRepository.findByEmail(request.getEmail()).get();

        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_USER));

        GroupMember groupMember = GroupMember.builder().user(user).build();
        groupMemberRepository.save(groupMember);

        TravelGroup travelGroup = travelGroupRepository.findById(planner.getTravelGroup().getTravelGroupId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_GROUP));
        travelGroup.addMembers(groupMember);
        travelGroupRepository.save(travelGroup);

        return user;
    }

    // 플래너에서 유저 검색
    @Transactional
    public Optional<User> searchUser (UserSearchRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        System.out.println("검색 유저:" + user);

        return user;
    }
}
