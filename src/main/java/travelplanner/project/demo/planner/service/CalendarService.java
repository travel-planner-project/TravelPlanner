package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.planner.domain.Calendar;
import travelplanner.project.demo.planner.domain.CalendarEditor;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.dto.request.CalendarCreateRequest;
import travelplanner.project.demo.planner.dto.request.CalendarEditRequest;
import travelplanner.project.demo.planner.dto.response.CalendarResponse;
import travelplanner.project.demo.planner.repository.CalendarRepository;
import travelplanner.project.demo.planner.repository.PlannerRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final PlannerRepository plannerRepository;

    public void createDate(Long plannerId, CalendarCreateRequest createRequest) {

        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PLANNER_NOT_FOUND)); // 플래너를 찾을 수 없는 경우 예외 발생

        Calendar buildRequest = Calendar.builder()
                .eachDate(createRequest.getEachDate())
                .planner(planner)
                .build();

        calendarRepository.save(buildRequest);
    }

    public void deleteDate(Long deleteId){

        // 검증 로직 필요
        Calendar calendar = calendarRepository.findById(deleteId)
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));

        // Planner 객체에서 Calendar 객체를 제거
        Planner planner = calendar.getPlanner();
        planner.getCalendars().remove(calendar);

        calendarRepository.delete(calendar);
    }

    public void updateDate(Long updateId, CalendarEditRequest updateRequest) {

        // 검증 로직 필요
        Calendar calendar = calendarRepository.findById(updateId)
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));

        CalendarEditor.CalendarEditorBuilder editorBuilder = calendar.toEditor();
        CalendarEditor calendarEditor = editorBuilder
                .eachDate(updateRequest.getEachDate())
                .build();
        calendar.edit(calendarEditor);
    }

    // 전체 Calendar 조회해서 response를 리스트로 리턴
    public List<CalendarResponse> getCalendarList() {
        List<Calendar> calendarList = calendarRepository.findAll();
        ArrayList<CalendarResponse> calendarResponses = new ArrayList<>();
        for(Calendar calendar : calendarList){

            CalendarResponse calendarResponse = CalendarResponse.builder()
                    .calendarId(calendar.getId())
                    .eachDate(calendar.getEachDate())
                    .createAt(calendar.getCreatedAt())
                    .plannerId(calendar.getPlanner().getId())
                    .build();
            calendarResponses.add(calendarResponse);
        }

        return calendarResponses;
    }
}
