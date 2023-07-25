package travelplanner.project.demo.planner.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.planner.domain.ToDo;
import travelplanner.project.demo.planner.domain.ToDoEditor;
import travelplanner.project.demo.planner.dto.request.ToDoCraeteRequest;
import travelplanner.project.demo.planner.dto.request.ToDoEditRequest;
import travelplanner.project.demo.planner.repository.ToDoRepository;

@Service
@RequiredArgsConstructor
public class ToDoService {


    private final ToDoRepository toDoRepository;

    public void addTodo(ToDoCraeteRequest request) {
        ToDo todo = ToDo.builder()
                .itemTitle(request.getItemTitle())
                .itemDate(request.getItemDate())
                .category(request.getCategory())
                .itemAddress(request.getItemAddress())
                .budget(request.getBudget())
                .isPrivate(request.getIsPrivate())
                .content(request.getContent())
                .build();
        toDoRepository.save(todo);
    }

    @Transactional
    public void editTodo(Long id, ToDoEditRequest editRequest) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(() -> new Exception(ExceptionType.NOT_EXISTS_TODO));

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
}
