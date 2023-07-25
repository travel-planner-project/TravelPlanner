package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.planner.domain.Date;
import travelplanner.project.demo.planner.dto.request.DateCreateRequest;
import travelplanner.project.demo.planner.dto.request.DateDeleteRequest;
import travelplanner.project.demo.planner.dto.request.DateUpdateRequest;
import travelplanner.project.demo.planner.repository.DateRepository;

import static travelplanner.project.demo.global.exception.ExceptionType.NOT_EXISTS_DATE;

@Service
@RequiredArgsConstructor
public class DateService {

    private final DateRepository dateRepository;

    public void addDate(DateCreateRequest createRequest) {
        Date buildRequest = Date.builder()
                .eachDate(createRequest.getEachDate())
                .build();
        dateRepository.save(buildRequest);
    }

    public void deleteDate(DateDeleteRequest deleteRequest){

        // 검증 로직 필요
        Date date = dateRepository.findById(deleteRequest.getDateId())
                .orElseThrow(() -> new Exception(NOT_EXISTS_DATE));
        dateRepository.delete(date);
    }

    public void updateDate(DateUpdateRequest updateRequest) {

        // 검증 로직 필요
        Date date = dateRepository.findById(updateRequest.getDateId())
                .orElseThrow(() -> new Exception(NOT_EXISTS_DATE));
    }


}