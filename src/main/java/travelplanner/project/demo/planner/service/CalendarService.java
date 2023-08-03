package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.planner.domain.Calendar;
import travelplanner.project.demo.planner.dto.request.CalendarCreateRequest;
import travelplanner.project.demo.planner.dto.request.CalendarDeleteRequest;
import travelplanner.project.demo.planner.dto.request.CalendarUpdateRequest;
import travelplanner.project.demo.planner.repository.CalendarRepository;


@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public void addDate(CalendarCreateRequest createRequest) {
        Calendar buildRequest = Calendar.builder()
                .eachDate(createRequest.getEachDate())
                .build();
        calendarRepository.save(buildRequest);
    }

    public void deleteDate(CalendarDeleteRequest deleteRequest){

        // 검증 로직 필요
        Calendar calendar = calendarRepository.findById(deleteRequest.getDateId())
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));
        calendarRepository.delete(calendar);
    }

    public void updateDate(CalendarUpdateRequest updateRequest) {

        // 검증 로직 필요
        Calendar calendar = calendarRepository.findById(updateRequest.getDateId())
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));
    }


}
