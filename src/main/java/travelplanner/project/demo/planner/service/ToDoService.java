package travelplanner.project.demo.planner.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.planner.domain.ToDo;
import travelplanner.project.demo.planner.dto.request.ToDoRequest;
import travelplanner.project.demo.planner.dto.response.ToDoResponse;
import travelplanner.project.demo.planner.repository.ToDoRepository;

@Service
@RequiredArgsConstructor
public class ToDoService {


    private final ToDoRepository toDoRepository;
    public void addTodo(ToDoRequest request) {
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

}
