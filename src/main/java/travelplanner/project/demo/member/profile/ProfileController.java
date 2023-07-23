package travelplanner.project.demo.member.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.member.profile.Service.ProfileService;
import travelplanner.project.demo.member.profile.Service.UserService;
import travelplanner.project.demo.member.profile.dto.request.PasswordCheckRequest;
import travelplanner.project.demo.member.profile.dto.request.PasswordUpdateRequest;
import travelplanner.project.demo.member.profile.dto.response.ProfileResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;


    // 특정 유저의 프로필
    @GetMapping("")
    public ResponseEntity<ProfileResponse> findUserProfile(@RequestParam Long userId) throws Exception {
        return ResponseEntity.ok(profileService.findUserProfile(userId));
    }

    // 프로필 수정
    @PutMapping("")
    public void updateUserProfile(
            @RequestParam Long userId, @RequestParam MultipartFile imgFile, @RequestParam String userNickname) throws Exception, IOException {

            profileService.updateUserProfileImg(userId, imgFile, userNickname);
    }

    //  회원 비밀번호 확인
    @PostMapping("/user")
    public boolean checkUserPassword(@RequestBody PasswordCheckRequest request) throws Exception {
        return userService.checkUserPassword(request);
    }

    // 회원 비밀번호 변경
    @PatchMapping("/user")
    public void updateUserPassword(@RequestBody PasswordUpdateRequest request) throws Exception {
        userService.updatePassword(request);
    }

    // 회원 탈퇴
    @DeleteMapping("/user")
    public void deleteUser(@RequestBody PasswordCheckRequest request) throws Exception{

        if (userService.checkUserPassword(request)) {
            userService.deleteUser(request);

        } else {
            throw new Exception(ExceptionType.CHECK_PASSWORD_AGAIN);
        }
    }
}
