package server.domain.planner.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.domain.planner.dto.request.*;
import server.domain.planner.dto.response.PlannerDetailResponse;
import server.domain.planner.dto.response.PlannerListResponse;
import server.domain.planner.service.PlannerService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/planner")
@AllArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    // 플래너 리스트 뷰
    @ApiOperation(
            value = "플래너 리스트 조회"
            , notes = "사용자의 인덱스를 통해 사용자의 플래너를 조회")
    @ApiImplicitParam(
            name = "userId"
            , value = "사용자 인덱스"
            , required = true
            , dataType = "string"
            , paramType = "query"
            , defaultValue = "None"
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "SUCCESS")
                    , @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
            }
    )
    @GetMapping(value = "")
    public Page<PlannerListResponse> getPlannerList(
            @RequestParam Long userId, final Pageable pageable
    ) {
        return plannerService.findPlannerListByUserId(userId, pageable).map(PlannerListResponse::new);
    }


    @ApiOperation(value = "플래너 생성")
    @ApiResponses(
            {
                    @ApiResponse(code = 201, message = "CREATE SUCCESS")
                    , @ApiResponse(code = 400, message = "BAD REQUEST: PLEASE CHECK INPUT DATA")
                    , @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
            }
    )
    @PostMapping(value = "")
    public void createPlanner(
            @RequestBody PlannerCreateRequest request, HttpServletRequest httpRequest
    ) {
        plannerService.createPlanner(request, httpRequest);
    }


    @ApiOperation(value = "플래너 수정")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "UPDATE SUCCESS")
                    , @ApiResponse(code = 400, message = "BAD REQUEST: PLEASE CHECK INPUT DATA")
                    , @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
            }
    )
    @PatchMapping(value = "")
    public void updatePlanner(
            @RequestBody PlannerUpdateRequest request
    ) {
        plannerService.updatePlanner(request);

        System.out.println("수정 " + request);
    }


    @ApiOperation(
            value = "플래너 삭제"
            , notes = "플래너 인덱스를 이용해 플래너 삭제")
    @ApiImplicitParam(
            name = "plannerId"
            , value = "플래너 인덱스"
            , required = true
            , dataType = "integer"
            , paramType = "query"
            , defaultValue = "None"
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "DELETE SUCCESS")
                    , @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
            }
    )
    @DeleteMapping(value = "")
    public void deletePlanner(
            @RequestParam Long plannerId, HttpServletRequest request
    ) {
        plannerService.deletePlanner(plannerId, request);
    }


    @ApiOperation(
            value = "플래너 상세 조회"
            , notes = "특정 플래너 인덱스로 해당 플래너 세부 내용 조회")
    @ApiImplicitParam(
            name = "plannerId"
            , value = "플래너 인덱스"
            , required = true
            , dataType = "integer"
            , paramType = "query"
            , defaultValue = "None"
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "UPDATE SUCCESS")
                    , @ApiResponse(code = 400, message = "BAD REQUEST: PLEASE CHECK INPUT DATA")
                    , @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
            }
    )
    @GetMapping(value = "detail")
    @ResponseBody
    public PlannerDetailResponse plannerDetail(
            @RequestParam Long plannerId
    ) {
        return plannerService.findPlannerByPlannerId(plannerId);
    }


    // 그룹멤버 추가
    @MessageMapping("/add-member/{plannerId}")
    public void addGroupMember(
            @DestinationVariable("plannerId") Long plannerId,
            GroupMemberUpdateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message" + plannerId
                , Map.of(
                        "type", "add-member",
                        "msg", plannerService.addGroupMember(request, plannerId)
                )
        );
    }

    // 그룹 멤버 삭제
    @MessageMapping("/delete-member/{plannerId}")
    public void deleteGroupMember(
            @DestinationVariable("plannerId") Long plannerId,
            GroupMemberDeleteRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend( "/sub/planner-message" + plannerId
                , Map.of(
                        "type", "delete-user"
                        , "msg", plannerService.deleteGroupMember(request)
                )
        );
    }

    // 유저 검색
    @MessageMapping("/search-user/{plannerId}")
    public void searchGroup(
            @DestinationVariable("plannerId") Long plannerId
            , UserSearchRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend("/sub/planner-message" + plannerId
                , Map.of(
                        "type", "search-user"
                        ,"msg", plannerService.searchUser(request)
                )
        );
    }
}