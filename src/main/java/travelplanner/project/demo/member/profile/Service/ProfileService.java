package travelplanner.project.demo.member.profile.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.member.profile.dto.request.ProfileUpdateRequest;
import travelplanner.project.demo.member.profile.dto.response.ProfileResponse;
import travelplanner.project.demo.member.profile.dto.response.ProfileUpdateResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProfileService {

    private final TokenUtil tokenUtil;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final S3Service s3Service;

    // 특정 유저의 프로필 찾기
    @Transactional
    public ProfileResponse findUserProfile(Long userId, HttpServletRequest request) throws Exception {

        // 헤더에서 JWT 토큰 추출
        String accessToken = tokenUtil.getJWTTokenFromHeader(request);

        // JWT 토큰을 이용하여 유저 정보를 추출
        String email = tokenUtil.getEmail(accessToken);

        // 이메일을 기반으로 유저 정보를 찾는 로직 추가 (예를 들어, 데이터베이스에서 해당 이메일에 해당하는 유저 정보를 가져올 수 있음)
        Optional<Member> member = memberRepository.findByEmail(email);

        if (!member.isPresent()) {
            throw new Exception(ExceptionType.USER_NOT_FOUND);
        }

        Profile profile = profileRepository.findProfileByMemberUserId(userId);
        if (profile == null) {
            profile = Profile.builder()
                    .member(member.get())
                    .build();

            profileRepository.save(profile);
        }

        // 로그인한 유저와 요청한 유저가 동일한지 확인
        boolean isCurrentUser = member.get().getUserId().equals(userId);

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setProfileImgUrl(profile.getProfileImgUrl());
        profileResponse.setEmail(profile.getMember().getEmail());
        profileResponse.setUserNickname(profile.getMember().getUserNickname());
        profileResponse.setCheckUser(isCurrentUser);

        return profileResponse;
    }

    // 프로필 수정 [START] =========================================================================================
    @Transactional
    public ProfileUpdateResponse updateUserProfileImg(ProfileUpdateRequest profileUpdateRequest, MultipartFile profileImg,  HttpServletRequest request) throws Exception, IOException {

        // 헤더에서 JWT 토큰 추출
        String accessToken = tokenUtil.getJWTTokenFromHeader(request);

        // JWT 토큰을 이용하여 유저 정보를 추출
        String email = tokenUtil.getEmail(accessToken);

        // 이메일을 기반으로 유저 정보를 찾는 로직 추가 (예를 들어, 데이터베이스에서 해당 이메일에 해당하는 유저 정보를 가져올 수 있음)
        Member member = memberRepository.findByEmail(email).get();

        if (member == null) {
            throw new Exception(ExceptionType.USER_NOT_FOUND);
        }

        // 로그인한 유저와 요청한 유저가 동일한지 확인
        boolean isCurrentUser = member.getUserId().equals(profileUpdateRequest.getUserId());

        if (!isCurrentUser) {
            throw new Exception(ExceptionType.THIS_USER_IS_NOT_SAME_LOGIN_USER);
        }

        Profile profile = profileRepository.findProfileByMemberUserId(profileUpdateRequest.getUserId());
        member.setUserNickname(profileUpdateRequest.getUserNickname());

        memberRepository.save(member);

        ProfileUpdateResponse response = new ProfileUpdateResponse();
        response.setUserNickname(member.getUserNickname());

        if (profileImg.isEmpty()) {

            if (!profileUpdateRequest.getChangeProfileImg()) { // 이미지 안바꿈
                response.setProfileImgUrl(profile.getProfileImgUrl());

            } else { // 이미지 바꿈 - 파일 입력 안함
                profile.setProfileImgUrl("");
                profile.setKeyName("");
                profileRepository.save(profile);
                response.setProfileImgUrl(profile.getProfileImgUrl());
            }

        } else { // 이미지 바꿈 - 파일 입력

            // 프로필 이미지가 있는 경우, 삭제하고 진행
            s3Service.deleteFile(profile.getKeyName());

            String originalImgName = profileImg.getOriginalFilename();
            String uniqueImgName = generateUniqueImgName(originalImgName, member.getUserId());

            // 업로드할 파일을 시스템의 기본 임시 디렉토리에 저장
            String localFilePath = System.getProperty("java.io.tmpdir") + "/" + uniqueImgName;
            profileImg.transferTo(Paths.get(localFilePath));

            // S3 에 이미지 업로드
            s3Service.uploadFile(uniqueImgName, localFilePath);
            String imgUrl = "https://travel-planner-buckets.s3.ap-northeast-2.amazonaws.com/upload/profile/";
            profile.setProfileImgUrl(imgUrl + uniqueImgName);
            profile.setKeyName(uniqueImgName);
            profileRepository.save(profile);

            // 로컬 임시 파일 삭제
            deleteLocalFile(localFilePath);

            response.setProfileImgUrl(profile.getProfileImgUrl());
        }

        return response;
    }

    private String generateUniqueImgName(String originalImgName, Long loginUserId) {
        return loginUserId + "_" + LocalDate.now() + "_" + System.currentTimeMillis() + getFileExtension(originalImgName);
    }

    private String getFileExtension(String imgName) {

        int dotIndex = imgName.lastIndexOf('.');
        return dotIndex == -1 ? "" : imgName.substring(dotIndex);
    }

    private void deleteLocalFile(String localFilePath) {

        File file = new File(localFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    // 프로필 수정 [END] =========================================================================================
}