package travelplanner.project.demo.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.planner.dto.request.GroupMemberCreateRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberDeleteRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberSearchRequest;
import travelplanner.project.demo.planner.service.GroupMemberService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GroupMemberController {


    private final SimpMessagingTemplate messagingTemplate;
    private final GroupMemberService groupMemberService;
    private final TokenUtil tokenUtil;

    @MessageMapping("/search-member/{plannerId}")
    public void searchGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberSearchRequest request,
            @Header("Authorization") String authorization
    ) throws Exception{

        tokenUtil.getJWTTokenFromWebSocket(authorization);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","search-user", "msg", groupMemberService.searchMember(request)
                )
        );
    }

    @MessageMapping("/add-member/{plannerId}")
    public void addGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberCreateRequest request,
            @Header("Authorization") String authorization
    ) throws Exception{

        // 리스트를 반환해줘야 하나..?
        tokenUtil.getJWTTokenFromWebSocket(authorization);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","add-user", "msg", groupMemberService.addGroupMember(request, plannerId)
                )
        );
    }

    @MessageMapping("/delete-member/{plannerId}")
    public void deleteGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberDeleteRequest request,
            @Header("Authorization") String authorization
    ) throws Exception {

        tokenUtil.getJWTTokenFromWebSocket(authorization);
        groupMemberService.deleteGroupMember(request);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId);
    }
}
