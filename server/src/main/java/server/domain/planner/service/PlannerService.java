package server.domain.planner.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.planner.domain.Planner;
import server.domain.planner.dto.request.PlannerCreateRequest;
import server.domain.planner.dto.request.PlannerUpdateRequest;
import server.domain.planner.dto.response.PlannerDetailResponse;
import server.domain.planner.dto.response.PlannerListResponse;
import server.domain.planner.repository.PlannerRepository;
import server.global.code.ErrorCode;
import server.global.exception.HandlableException;
import server.global.util.paging.Paging;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;


    // 플래너 리스트
    public Map<String, Object> findPlannerListByUserNickname (String userNickname, Pageable pageable) {

        Page<Planner> page = plannerRepository.findByUserNickname(userNickname, pageable);

        Paging paging =

                Paging.builder()
                        .page(page)
                        .blockCnt(5)
                        .build();

        return Map.of ("plannerList", PlannerListResponse.plannerListResponses(page.getContent()),"paging", paging);
    }

    // 플래너 생성
    @Transactional
    public void createPlanner (PlannerCreateRequest dto) {

        Planner planner = Planner.createPlanner(dto);

        plannerRepository.saveAndFlush(planner);
    }

    // 플래너 제거
    @Transactional
    public void deletePlanner (Long plannerId) {

        // 제거할 플래너 찾기
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        plannerRepository.delete(planner);
    }

    // 플래너 세부내용
    public PlannerDetailResponse findPlannerByPlannerId (Long plannerId) {

        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        return new PlannerDetailResponse(planner);
    }

    // 플래너 제목 변경
    @Transactional
    public void updatePlanner(PlannerUpdateRequest dto) {

        Planner planner = plannerRepository.findById(dto.getPlannerId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        planner.updatePlanner(dto);

        System.out.println("플래너 제목: " + dto.getPlanTitle());
        System.out.println("플래너 공개여부: " + dto.getIsPrivate());

        plannerRepository.flush();
    }
}
