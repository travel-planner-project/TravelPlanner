package travelplanner.project.demo.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.planner.dto.request.GroupMemberCreateRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberSearchRequest;
import travelplanner.project.demo.planner.service.GroupMemberService;

@RequiredArgsConstructor
public class GroupMemberController {

    private SimpMessagingTemplate messagingTemplate;
    private final GroupMemberService groupMemberService;
    private final TokenUtil tokenUtil;

    @MessageMapping("/search-member/{plannerId}")
    public void searchGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberSearchRequest request,
            @Header("Authorization") String athorization
    ) throws Exception{

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, groupMemberService.searchMember(request));
    }

    @MessageMapping("/add-member/{plannerId}")
    public void addGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberCreateRequest request,
            @Header("Authorization") String athorization
    ) throws Exception{

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, groupMemberService.addGroupMember(request,plannerId));
    }
}
