package server.domain.planner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.planner.domain.Planner;
import server.domain.planner.domain.travelGroup.GroupMember;
import server.domain.planner.domain.travelGroup.GroupMemberType;
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
import server.global.security.UserDetailsImpl;
import server.global.security.UserDetailsServiceImpl;
import server.global.security.jwt.JwtUtil;

import javax.servlet.http.HttpServletRequest;
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
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    // 플래너 리스트
    @Transactional(readOnly = true)
    public Page<Planner> findPlannerListByUserId (Long userId, Pageable pageable) {
        return plannerRepository.findByTravelGroupGroupMembersUserUserId(userId, pageable);
    }

    // 플래너 생성
    @Transactional
    public void createPlanner (PlannerCreateRequest request, HttpServletRequest httpRequest) {

        // 현재 사용자 정보
        String token = jwtUtil.getHeaderToken(httpRequest, "Access");
        String email = jwtUtil.getEmailFromToken(token);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(email);
        Long userId = userDetails.getUser().getUserId();

        Optional<User> user = userRepository.findByUserId(userId);

        GroupMember groupMember = GroupMember.builder()
                .user(user.get())
                .userId(user.get().getUserId())
                .userNickname(user.get().getUserNickname())
                .groupMemberType(GroupMemberType.HOST)
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
    public void deletePlanner (Long plannerId, HttpServletRequest request) {

        // 제거할 플래너 찾기
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        // 현재 사용자 정보
        String token = jwtUtil.getHeaderToken(request, "Access");
        String email = jwtUtil.getEmailFromToken(token);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(email);
        Long userId = userDetails.getUser().getUserId();

        // 플래너를 만든 사람이 내가 아니면, 플래너의 그룹에서 '나' 를 제거해야 한다.
        if (!userId.equals(planner.getUserId())) {

            TravelGroup travelGroup = travelGroupRepository.findById(planner.getTravelGroup().getTravelGroupId())
                    .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_GROUP_MEMBER));

            List<GroupMember> groupMembers = travelGroup.getGroupMembers();

            for(GroupMember groupMember: groupMembers) {

                if (groupMember.getUser().getUserId().equals(userId)) {
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
    public Optional<User> addGroupMember (GroupMemberUpdateRequest request, Long plannerId) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_USER));

        if (groupMemberRepository.findByUserId(user.get().getUserId()) == null) {

            GroupMember groupMember = GroupMember.builder()
                    .user(user.get())
                    .userId(user.get().getUserId())
                    .userNickname(user.get().getUserNickname())
                    .groupMemberType(GroupMemberType.MEMBER)
                    .build();

            groupMemberRepository.save(groupMember);

            TravelGroup travelGroup = travelGroupRepository.findById(planner.getTravelGroup().getTravelGroupId())
                    .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_GROUP));
            travelGroup.addMembers(groupMember);
            travelGroupRepository.save(travelGroup);

            return user;

        }

        System.out.println("이미 해당 유저가 존재합니다.");

        return null;
    }

    // 플래너에서 유저 검색
    @Transactional
    public Optional<User> searchUser (UserSearchRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (!user.isPresent()) {
            System.out.println("해당 유저가 존재하지 않습니다.");
        }

        return user;
    }
}
