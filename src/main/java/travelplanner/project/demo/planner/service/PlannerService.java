package travelplanner.project.demo.planner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.repository.PlannerRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PlannerService {

    private final MemberRepository memberRepository;
    private final PlannerRepository plannerRepository;


    //플래너 리스트
    public Page<Planner> findPlannerListByUserId (Long userId, Pageable pageable){
        return plannerRepository.findByUserId(userId, pageable);
    }

    //플래너 삭제
    public void deletePlanner(Long plannerId, HttpServletRequest request){
        Optional<Planner> planner = plannerRepository.findById(plannerId);

        //플래너가 존재하지 않을 때 예외처리
        if (planner.isPresent() == false){
            throw new Exception(ExceptionType.NOT_EXISTS_PLANNER);
        }

        //현재 사용자 정보




    }
}

