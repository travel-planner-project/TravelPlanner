package travelplanner.project.demo.domain.planner.groupmember.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.planner.groupmember.dto.request.GroupMemberCreateRequest;
import travelplanner.project.demo.domain.planner.groupmember.dto.request.GroupMemberDeleteRequest;
import travelplanner.project.demo.domain.search.MemberSearchResponse;
import travelplanner.project.demo.domain.planner.groupmember.service.GroupMemberService;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;
import travelplanner.project.demo.global.util.TokenUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name = "Planner", description = "플래너 API")
@Controller
@RequiredArgsConstructor
public class GroupMemberController {


    private final SimpMessagingTemplate messagingTemplate;
    private final GroupMemberService groupMemberService;
    private final TokenUtil tokenUtil;


    @MessageMapping("/add-member/{plannerId}")
    public void addGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberCreateRequest request,
            @Header("Authorization") String accessToken
    ) throws Exception{

        // 리스트를 반환해줘야 하나..?
        tokenUtil.getAuthenticationFromToken(accessToken);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","add-user", "msg", groupMemberService.addGroupMember(request, plannerId)
                )
        );
    }

    @MessageMapping("/delete-member/{plannerId}")
    public void deleteGroupMember(
            @DestinationVariable Long plannerId,
            GroupMemberDeleteRequest request,
            @Header("Authorization") String accessToken
    ) throws Exception {

        tokenUtil.getUserIdFromToken(accessToken);
        groupMemberService.deleteGroupMember(request);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, Collections.emptyMap());
    }
}
