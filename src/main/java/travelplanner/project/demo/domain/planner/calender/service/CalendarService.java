package travelplanner.project.demo.domain.planner.calender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.planner.calender.domain.Calendar;
import travelplanner.project.demo.domain.planner.calender.editor.CalendarEditor;
import travelplanner.project.demo.domain.planner.calender.dto.request.CalendarCreateRequest;
import travelplanner.project.demo.domain.planner.calender.dto.request.CalendarEditRequest;
import travelplanner.project.demo.domain.planner.calender.dto.response.CalendarResponse;
import travelplanner.project.demo.domain.planner.calender.repository.CalendarRepository;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;
import travelplanner.project.demo.domain.planner.todo.service.ToDoService;
import travelplanner.project.demo.domain.planner.validation.ValidatingService;
import travelplanner.project.demo.domain.planner.todo.dto.response.ToDoResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final ValidatingService validatingService;
    private final ToDoService toDoService;

    public CalendarResponse createDate(Long plannerId, CalendarCreateRequest createRequest, String accessToken) {

        Planner planner = validatingService.validatePlannerAndUserAccessForWebSocket(accessToken, plannerId);

        Calendar buildRequest = Calendar.builder()
                .dateTitle(createRequest.getDateTitle())
                .planner(planner)
                .createdAt(LocalDateTime.now())
                .build();

        calendarRepository.save(buildRequest);

        List<ToDoResponse> scheduleItemList = toDoService.getScheduleItemList(buildRequest.getId());

        return CalendarResponse.builder()
                .dateId(buildRequest.getId())
                .createAt(buildRequest.getCreatedAt())
                .dateTitle(buildRequest.getDateTitle())
                .plannerId(plannerId)
                .scheduleItemList(scheduleItemList)
                .build();
    }

    @Transactional
    public void deleteDate(Long plannerId, Long deleteId, String accessToken){

        // 반환값 무시하고 검증만 함
        Planner planner = validatingService.validatePlannerAndUserAccessForWebSocket(accessToken, plannerId);
        Calendar calendar = validatingService.validateCalendarAccess(planner, deleteId);

        // 캘린더에서 플래너를 갖고 옴
        planner = calendar.getPlanner();

        // 플래너의 calendar 리스트에서 캘린더 제거
        planner.getCalendars().remove(calendar);

        calendarRepository.delete(calendar);
    }

    public CalendarResponse updateDate(Long plannerId, Long updateId, CalendarEditRequest updateRequest, String accessToken) {

        Planner planner = validatingService.validatePlannerAndUserAccessForWebSocket(accessToken, plannerId);
        Calendar calendar = validatingService.validateCalendarAccess(planner, updateId);
        CalendarEditor.CalendarEditorBuilder editorBuilder = calendar.toEditor();
        CalendarEditor calendarEditor = editorBuilder
                .dateTitle(updateRequest.getDateTitle())
                .build();
        calendar.edit(calendarEditor);
        calendarRepository.updatedateTitle(updateId, updateRequest.getDateTitle());

        List<ToDoResponse> scheduleItemList = toDoService.getScheduleItemList(calendar.getId());

        return CalendarResponse.builder()
                .dateId(calendar.getId())
                .createAt(calendar.getCreatedAt())
                .dateTitle(calendar.getDateTitle())
                .plannerId(plannerId)
                .scheduleItemList(scheduleItemList)
                .build();
    }

    // 전체 Calendar 조회해서 response를 리스트로 리턴
    public List<CalendarResponse> getCalendarList() {
        List<Calendar> calendarList = calendarRepository.findAll();
        ArrayList<CalendarResponse> calendarResponses = new ArrayList<>();
        for(Calendar calendar : calendarList){

            CalendarResponse calendarResponse = CalendarResponse.builder()
                    .dateId(calendar.getId())
                    .dateTitle(calendar.getDateTitle())
                    .createAt(calendar.getCreatedAt())
                    .plannerId(calendar.getPlanner().getId())
                    .build();
            calendarResponses.add(calendarResponse);
        }

        return calendarResponses;
    }

    // 플래너 서비스에서 특정 플래너에 포함된 캘린더 및 투두를 갖고오기 위해 오버로딩
    public List<CalendarResponse> getCalendarList(Long plannerId) {

        List<Calendar> calendarList = calendarRepository.findByPlannerId(plannerId);
        ArrayList<CalendarResponse> calendarResponses = new ArrayList<>();

        for (Calendar calendar : calendarList) {
            CalendarResponse calendarResponse = CalendarResponse.builder()
                    .dateId(calendar.getId())
                    .dateTitle(calendar.getDateTitle())
                    .createAt(calendar.getCreatedAt())
                    .plannerId(calendar.getPlanner().getId())
                    .build();
            calendarResponses.add(calendarResponse);
        }
        return calendarResponses;
    }
}
