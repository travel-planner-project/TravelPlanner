package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.planner.domain.PlannerDate;
import travelplanner.project.demo.planner.dto.request.DateCreateRequest;
import travelplanner.project.demo.planner.dto.request.DateDeleteRequest;
import travelplanner.project.demo.planner.dto.request.DateUpdateRequest;
import travelplanner.project.demo.planner.repository.PlannerDateRepository;

import static travelplanner.project.demo.global.exception.ExceptionType.NOT_EXISTS_DATE;

@Service
@RequiredArgsConstructor
public class PlannerDateService {

    private final PlannerDateRepository plannerDateRepository;

    public void addDate(DateCreateRequest createRequest) {
        PlannerDate buildRequest = PlannerDate.builder()
                .eachDate(createRequest.getEachDate())
                .build();
        plannerDateRepository.save(buildRequest);
    }

    public void deleteDate(DateDeleteRequest deleteRequest){

        // 검증 로직 필요
        PlannerDate plannerDate = plannerDateRepository.findById(deleteRequest.getDateId())
                .orElseThrow(() -> new Exception(NOT_EXISTS_DATE));
        plannerDateRepository.delete(plannerDate);
    }

    public void updateDate(DateUpdateRequest updateRequest) {

        // 검증 로직 필요
        PlannerDate plannerDate = plannerDateRepository.findById(updateRequest.getDateId())
                .orElseThrow(() -> new Exception(NOT_EXISTS_DATE));
    }


}
