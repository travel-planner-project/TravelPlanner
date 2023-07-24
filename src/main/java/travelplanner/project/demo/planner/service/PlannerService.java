package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import travelplanner.project.demo.planner.dto.request.PlannerUpdateRequest;
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
    public Page<Planner> findPlannerListByUserId (Long userId, Pageable pageable){
        return plannerRepository.findByUserId(userId, pageable);
    }

    //플래너 삭제
    public void deletePlanner(Long plannerId){

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new Exception(NOT_EXISTS_PLANNER));
        ;
        // 현재 사용자 id 갖고 오기
        Member currentMember = getCurrentMember();
        // if (플래너 작성자의 index != 현재 사용자 index)
        if (!planner.getMember().getUserId().equals(currentMember.getUserId())) {
            throw new Exception(PLANER_NOT_AUTHORIZED);
        }
        plannerRepository.delete(planner);
    }

    public void createPlanner(PlannerCreateRequest request) {
        Planner createPlanner = Planner.builder()
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        plannerRepository.save(createPlanner);
    }

    @Transactional
    public void updatePlanner(Long plannerId, PlannerUpdateRequest request) {

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new Exception(NOT_EXISTS_PLANNER));

        // 현재 사용자 id 갖고 오기
        Member currentMember = getCurrentMember();
        // if (플래너 작성자의 index != 현재 사용자 index)
        if(!planner.getMember().getUserId().equals(currentMember.getUserId())){
            throw new Exception(PLANER_NOT_AUTHORIZED);
        }
        PlannerEditor.PlannerEditorBuilder editorBuilder = planner.toEditor();
        PlannerEditor plannerEditor = editorBuilder
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        planner.edit(plannerEditor, currentMember);
    }

    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }
}

