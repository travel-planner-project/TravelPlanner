package travelplanner.project.demo.domain.search;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;

import java.util.List;

@Tag(name = "Search", description = "유저 검색 API")
@Controller
@RequiredArgsConstructor
public class MemberSearchController {

    private final MemberSearchService memberSearchService;

    @Operation(summary = "유저 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 검색 성공"),
            @ApiResponse(responseCode = "404", description = "유저가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping("/search/member")
    @ResponseBody
    public List<MemberSearchResponse> searchGroupMember(
            @Parameter(name = "email", description = "이메일", in = ParameterIn.QUERY)
            @RequestParam String email){
        return memberSearchService.searchMember(email);
    }
}
