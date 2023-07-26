package travelplanner.project.demo.member.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.member.profile.Service.ProfileService;
import travelplanner.project.demo.member.profile.Service.UserService;
import travelplanner.project.demo.member.profile.dto.request.PasswordCheckRequest;
import travelplanner.project.demo.member.profile.dto.request.PasswordUpdateRequest;
import travelplanner.project.demo.member.profile.dto.request.ProfileUpdateRequest;
import travelplanner.project.demo.member.profile.dto.response.ProfileResponse;
import travelplanner.project.demo.member.profile.dto.response.ProfileUpdateResponse;

import java.io.IOException;


@Tag(name = "Profile", description = "프로필 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;


    @Operation(summary = "프로필 상세")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 유저의 프로필 접근 성공",
                    content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "403", description = "로그인한 유저와 프로필 유저가 같지 않은 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "특정 유저를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @GetMapping("")
    public ResponseEntity<ProfileResponse> findUserProfile(
            @Parameter(name = "userId", description = "유저 인덱스", in = ParameterIn.QUERY) // swagger
            @RequestParam Long userId) throws Exception {
        return ResponseEntity.ok(profileService.findUserProfile(userId));
    }


    @Operation(summary = "프로필 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 유저의 프로필 수정 성공"),
            @ApiResponse(responseCode = "403", description = "로그인한 유저와 프로필 유저가 같지 않은 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "특정 유저를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileUpdateResponse> updateUserProfile(
            @Parameter(name = "profileUpdateRequest", description = "프로필 수정 요청", in = ParameterIn.QUERY) // swagger
            @RequestPart ProfileUpdateRequest profileUpdateRequest,
            @Parameter(name = "profileImg", description = "프로필 이미지", in = ParameterIn.QUERY) // swagger
            @RequestPart MultipartFile profileImg) throws Exception, IOException {

        return ResponseEntity.ok(profileService.updateUserProfileImg(profileUpdateRequest, profileImg));
    }


    @Operation(summary = "회원 수정 : 비밀번호 확인 페이지")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 페이지 접근 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @GetMapping("/user/check")
    public void getCheckPasswordPage() {
    }


    @Operation(summary = "회원 수정 : 비밀번호 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 일치 확인"),
            @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "403", description = "로그인한 유저와 프로필 유저가 같지 않은 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "특정 유저를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PostMapping("/user/check")
    public boolean checkUserPassword(@RequestBody PasswordCheckRequest request) throws Exception {
        return userService.checkUserPassword(request);
    }


    @Operation(summary = "회원 수정 : 비밀번호 변경 페이지")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 페이지 접근 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @GetMapping("/user/updateInfo")
    public void getUpdateUserPasswordPage() {
    }


    @Operation(summary = "회원 수정 : 비밀번호 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "403", description = "로그인한 유저와 프로필 유저가 같지 않은 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "특정 유저를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PatchMapping("/user/updateInfo")
    public void updateUserPassword(@RequestBody PasswordUpdateRequest request) throws Exception {
        userService.updatePassword(request);
    }


    @Operation(summary = "회원 수정 : 회원 탈퇴 페이지")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 페이지 접근 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @GetMapping("/user/delete")
    public void getDeleteUserPage() {
    }


    @Operation(summary = "회원 수정 : 회원 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "403", description = "로그인한 유저와 프로필 유저가 같지 않은 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "특정 유저를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @DeleteMapping("/user/delete")
    public void deleteUser(@RequestBody PasswordCheckRequest request) throws Exception{

        if (userService.checkUserPassword(request)) {
            userService.deleteUser(request);

        } else {
            throw new Exception(ExceptionType.CHECK_PASSWORD_AGAIN);
        }
    }
}
