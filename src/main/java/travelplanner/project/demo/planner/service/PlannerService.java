package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.domain.PlannerEditor;
import travelplanner.project.demo.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.planner.dto.request.PlannerDeleteRequest;
import travelplanner.project.demo.planner.dto.request.PlannerUpdateRequest;
import travelplanner.project.demo.planner.dto.response.PlannerDetailResponse;
import travelplanner.project.demo.planner.repository.PlannerRepository;

import static travelplanner.project.demo.global.exception.ExceptionType.NOT_EXISTS_PLANNER;
import static travelplanner.project.demo.global.exception.ExceptionType.PLANER_NOT_AUTHORIZED;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor
@RequiredArgsConstructor
public class PlannerService {

    private final MemberRepository memberRepository;
    private final PlannerRepository plannerRepository;

    //플래너 리스트
//    public Page<Planner> findPlannerListByUserId (Long userId, Pageable pageable){
//        return plannerRepository.findByUserId(userId, pageable);
//    }

    public PlannerDetailResponse getDetailPlanner(Long plannerId) {

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new Exception(NOT_EXISTS_PLANNER));

        PlannerDetailResponse plannerDetailResponse = PlannerDetailResponse.builder()
                .plannerId(planner.getId())
                .planTitle(planner.getPlanTitle())
                .isPrivate(planner.getIsPrivate())
                .startDate(planner.getStartDate())
                .endDate(planner.getEndDate())
                .build();
        return plannerDetailResponse;
    }

    //플래너 삭제
    public void deletePlanner(PlannerDeleteRequest plannerDeleteRequest) {


        Long plannerId = plannerDeleteRequest.getPlannerId();

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new Exception(NOT_EXISTS_PLANNER));

        Member currentMember = getCurrentMember();

        // 플래너를 생성한 사람이 아닐 경우
        if (!planner.getMember().getId().equals(currentMember.getId())) {

            throw new Exception(PLANER_NOT_AUTHORIZED);
        }
        plannerRepository.delete(planner);

    }


    public void createPlanner(PlannerCreateRequest request) {
        Planner createPlanner = Planner.builder()
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())

                // todo 이곳 말고 캘린더를 생성하는 서비스에서 플래너 sratdate, enddate 업데이트해주기
                .build();

        plannerRepository.save(createPlanner);
    }

    @Transactional
    public void updatePlanner(Long plannerId, PlannerUpdateRequest request) {

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new Exception(NOT_EXISTS_PLANNER));

        // TODO 플래너 엔티티를 지울 수 있는지에 대한 자격조건 확인해야함
        Member currentMember = getCurrentMember();

        // 플래너를 생성한 사람이 아닐 경우
        if (!planner.getMember().getId().equals(currentMember.getId())) {

            throw new Exception(PLANER_NOT_AUTHORIZED);
        }

        PlannerEditor.PlannerEditorBuilder editorBuilder = planner.toEditor();
        PlannerEditor plannerEditor = editorBuilder
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())
                .build();
        planner.edit(plannerEditor);
    }

    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }
}

