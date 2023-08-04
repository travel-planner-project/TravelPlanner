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
import travelplanner.project.demo.planner.domain.ToDo;
import travelplanner.project.demo.planner.domain.ToDoEditor;
import travelplanner.project.demo.planner.dto.request.ToDoCraeteRequest;
import travelplanner.project.demo.planner.dto.request.ToDoEditRequest;
import travelplanner.project.demo.planner.dto.response.ToDoResponse;
import travelplanner.project.demo.planner.repository.ToDoRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ToDoService {

    private final MemberRepository memberRepository;
    private final ToDoRepository toDoRepository;

    public List<ToDoResponse> getToDoList() {
        List<ToDo> ToDoList = toDoRepository.findAll();
        ArrayList<ToDoResponse> toDoResponses = new ArrayList<>();
        for (int i = 0; i < ToDoList.size(); i++){
            ToDo toDo = ToDoList.get(i);
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

    public void addTodo(ToDoCraeteRequest request) {
        ToDo todo = ToDo.builder()
                .itemTitle(request.getItemTitle())
                .itemDate(request.getItemDate())
                .category(request.getCategory())
                .content(request.getContent())
                .isPrivate(request.getIsPrivate())
                .budget(request.getBudget())
                .itemAddress(request.getItemAddress())
                .build();
        toDoRepository.save(todo);
    }

    @Transactional
    public void editTodo(Long id, ToDoEditRequest editRequest) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorType.TODO_NOT_FOUND));

        // TODO 투두 엔티티와 별개로 지울 수 있는지에 대한 자격조건 확인해야함

//        Member currentMember = getCurrentMember();
//        for(Member x: GroupMember){
//            Member member = GroupMember.getMemberName();
//            if (!currentMember.equals(member)) {
//                throw new Exception(TODO_NOT_AUTHORIZED);
//            }
//        }

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

        // TODO 투두 엔티티와 별개로 지울 수 있는지에 대한 자격조건 확인해야함

//        Member currentMember = getCurrentMember();
//        for(Member x: GroupMember){
//            Member member = GroupMember.getMemberName();
//            if (!currentMember.equals(member)) {
//                throw new Exception(TODO_NOT_AUTHORIZED);
//            }
//        }

        toDoRepository.delete(toDo);
    }

    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }
}

