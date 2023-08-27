package travelplanner.project.demo.planner.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.planner.dto.request.GroupMemberCreateRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberDeleteRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberSearchRequest;
import travelplanner.project.demo.planner.dto.response.GroupMemberSearchResponse;
import travelplanner.project.demo.planner.service.GroupMemberService;

import java.util.Collections;
import java.util.Map;

@Tag(name = "Planner", description = "플래너 API")
@Controller
@RequiredArgsConstructor
public class GroupMemberController {


    private final SimpMessagingTemplate messagingTemplate;
    private final GroupMemberService groupMemberService;
    private final TokenUtil tokenUtil;

    @Operation(summary = "멤버 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹 멤버 검색 성공"),
            @ApiResponse(responseCode = "404", description = "유저가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping("/search/member")
    @ResponseBody
    public GroupMemberSearchResponse searchGroupMember(@RequestBody GroupMemberSearchRequest request){
        return groupMemberService.searchMember(request);
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
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, Collections.emptyMap());
    }
}
