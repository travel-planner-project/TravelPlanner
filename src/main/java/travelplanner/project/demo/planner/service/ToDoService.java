package travelplanner.project.demo.planner.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.planner.domain.*;
import travelplanner.project.demo.planner.dto.request.ToDoCraeteRequest;
import travelplanner.project.demo.planner.dto.request.ToDoEditRequest;
import travelplanner.project.demo.planner.dto.response.ToDoResponse;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.ToDoRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ToDoService {

    private final MemberRepository memberRepository;
    private final ToDoRepository toDoRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final CalendarService calendarService;

    public List<ToDoResponse> getToDoList() {
        List<ToDo> ToDoList = toDoRepository.findAll();
        ArrayList<ToDoResponse> toDoResponses = new ArrayList<>();
        for (ToDo toDo : ToDoList){
            ToDoResponse toDoResponse = ToDoResponse.builder()
                    .dateId(toDo.getId())
                    .itemId(toDo.getCalendar().getId())
                    .category(toDo.getCategory())
                    .itemDate(toDo.getItemDate())
                    .itemContent(toDo.getContent())
                    .isPrivate(toDo.getIsPrivate())
                    .budget(toDo.getBudget())
                    .itemAddress(toDo.getItemAddress())
                    .build();
            toDoResponses.add(toDoResponse);
        }
        return toDoResponses;
    }

    public void createTodo(Long plannerId, Long dateId,
                           ToDoCraeteRequest request) {
        // 플래너와 사용자에 대한 검증
        Planner planner = calendarService.validatePlannerAndUserAccess(plannerId);
        // 캘린더에 대한 검증
        Calendar calendar = calendarService.validateCalendarAccess(planner, dateId);

        ToDo todo = ToDo.builder()
                .calendar(calendar)
                .itemTitle(request.getItemTitle())
                .itemDate(request.getItemDate())
                .category(request.getCategory())
                .content(request.getItemContent())
                .isPrivate(request.getIsPrivate())
                .budget(request.getBudget())
                .itemAddress(request.getItemAddress())
                .build();
        toDoRepository.save(todo);
    }

    @Transactional
    public void editTodo(
            Long plannerId, Long dateId, Long toDoId,
            ToDoEditRequest editRequest
    ) {

        // todo todo 검증 필요함
        ToDo toDo = toDoRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.TODO_NOT_FOUND));

        // 현재 로그인한 사람의 id가져오기
        Member currentMember = getCurrentMember();

        // 그룹멤버 전체 가져오기
        List<GroupMember> groupMemberList = groupMemberRepository.findAll();

        // 현재 로그인한 사람이 그룹멤버에 포함되지 않는다면
        for (GroupMember groupMember : groupMemberList) {
            if (!groupMember.getUserNickname().equals(currentMember.getUserNickname())) {
                throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
            }
        }
        // 수정 로직 시작
        ToDoEditor.ToDoEditorBuilder editorBuilder = toDo.toEditor();
        ToDoEditor toDoEditor = editorBuilder
                .itemTitle(editRequest.getItemTitle())
                .itemDate(editRequest.getItemDate())
                .category(editRequest.getCategory())
                .itemAddress(editRequest.getItemAddress())
                .budget(editRequest.getBudget())
                .isPrivate(editRequest.getIsPrivate())
                .content(editRequest.getContent())
                .build();
        toDo.edit(toDoEditor);
    }

    public void delete(Long deleteId) {

        ToDo toDo = toDoRepository.findById(deleteId)
                .orElseThrow(() -> new ApiException(ErrorType.TODO_NOT_FOUND));

        // 현재 로그인한 사람의 id가져오기
        Member currentMember = getCurrentMember();

        // 그룹멤버 전체 가져오기
        List<GroupMember> groupMemberList = groupMemberRepository.findAll();

        // 현재 로그인한 사람이 그룹멤버에 포함되지 않는다면
        // 현재 로그인한 사람이 그룹멤버에 포함되지 않는다면
        for (GroupMember groupMember : groupMemberList) {
            if (!groupMember.getUserNickname().equals(currentMember.getUserNickname())) {
                throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
            }
        }

        toDoRepository.delete(toDo);
    }

    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }
}

