package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.planner.domain.Calendar;
import travelplanner.project.demo.planner.dto.request.CalendarCreateRequest;
import travelplanner.project.demo.planner.dto.request.CalendarEditRequest;
import travelplanner.project.demo.planner.repository.CalendarRepository;


@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public void createDate(CalendarCreateRequest createRequest) {
        Calendar buildRequest = Calendar.builder()
                .eachDate(createRequest.getEachDate())
                .build();
        calendarRepository.save(buildRequest);
    }

    public void deleteDate(Long deleteId){

        // 검증 로직 필요
        Calendar calendar = calendarRepository.findById(deleteId)
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));

        calendarRepository.delete(calendar);
    }

    public void updateDate(Long updateId, CalendarEditRequest updateRequest) {

        // 검증 로직 필요
        Calendar calendar = calendarRepository.findById(updateId)
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));
    }


}
