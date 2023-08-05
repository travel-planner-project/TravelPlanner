package travelplanner.project.demo.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import travelplanner.project.demo.planner.dto.request.GroupMemberCreateRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberSearchRequest;
import travelplanner.project.demo.planner.service.GroupMemberService;

@RequiredArgsConstructor
public class GroupMemberController {

    private SimpMessagingTemplate messagingTemplate;
    private final GroupMemberService groupMemberService;

    @MessageMapping("pub/search-member/{plannerId}")
    public void searchGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberSearchRequest request
    ) throws Exception{
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, groupMemberService.searchMember(request));
    }

    @MessageMapping("pub/add-member/{plannerId}")
    public void addGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberCreateRequest request
    ) throws Exception{
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, groupMemberService.addGroupMember(request,plannerId));
    }
}
