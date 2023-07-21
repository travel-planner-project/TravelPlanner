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
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.global.security.AuthConfig;
import travelplanner.project.demo.global.security.jwt.JwtService;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.planner.repository.PlannerRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor
@RequiredArgsConstructor
public class PlannerService {

    private final MemberRepository memberRepository;
    private final PlannerRepository plannerRepository;
    private final JwtService jwtService;
    private final AuthConfig authConfig;


    //플래너 리스트
    public Page<Planner> findPlannerListByUserId (Long userId, Pageable pageable){
        return plannerRepository.findByUserId(userId, pageable);
    }

    //플래너 삭제
    public void deletePlanner(Long plannerId, HttpServletRequest request){
        Optional<Planner> planner = plannerRepository.findById(plannerId);

        //현재 사용자 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

        //플래너가 존재하지 않을 때 예외처리
        if (planner.isPresent() == false){
            throw new Exception(ExceptionType.NOT_EXISTS_PLANNER);
        }
    }

    public void createPlanner(PlannerCreateRequest request) {
        Planner createPlanner = Planner.builder()
                .userId(request.getUserId())
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())
//                .startDate(request.getStartDate())
//                .endDate(request.getEndDate())
                .build();

        plannerRepository.save(createPlanner);
    }
//  수정과 삭제할 때 이용하면 삭제 코드를 줄일 수 있습니다.
//    private Member getCurrentMember() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName(); // 현재 사용자의 email 얻기
//        return memberRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
//    }
}

