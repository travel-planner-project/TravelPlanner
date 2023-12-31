package travelplanner.project.demo.domain.profile.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.editor.MemberEditor;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.domain.profile.domain.Profile;
import travelplanner.project.demo.domain.profile.repository.ProfileRepository;
import travelplanner.project.demo.domain.profile.dto.response.ProfileResponse;
import travelplanner.project.demo.domain.profile.dto.response.ProfileUpdateResponse;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.S3Util;
import travelplanner.project.demo.domain.profile.domain.ProfileEditor;
import travelplanner.project.demo.domain.profile.dto.request.ProfileUpdateRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProfileService {

    private final AuthUtil authUtil;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final S3Util s3Util;

    // 특정 유저의 프로필 찾기
    @Transactional
    public ProfileResponse findUserProfile(Long userId) throws ApiException {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        Profile profile = profileRepository.findProfileByMemberId(member.getId());

        // 로그인한 유저와 요청한 유저가 동일한지 확인
        boolean isCurrentUser = member.getId().equals(userId);

        return ProfileResponse.builder()
                .email(profile.getMember().getEmail())
                .userNickname(profile.getMember().getUserNickname())
                .profileImgUrl(profile.getProfileImgUrl())
                .checkUser(isCurrentUser)
                .build();
    }

    // 프로필 수정 [START] =========================================================================================
    @Transactional
    public ProfileUpdateResponse updateUserProfileImg(ProfileUpdateRequest profileUpdateRequest, MultipartFile profileImg, HttpServletRequest request) throws Exception, IOException {

        Member member = authUtil.getCurrentMember(request);

        // 회원 닉네임 수정
        MemberEditor memberEditor = MemberEditor.builder()
                .userNickname(profileUpdateRequest.getUserNickname())
                .build();
        member.edit(memberEditor);

//        memberRepository.save(member);

//        ProfileUpdateResponse response = new ProfileUpdateResponse();
//        response.setUserNickname(member.getUserNickname());
// --------------------------------------------------------------------------------

        Profile profile = profileRepository.findProfileByMemberId(member.getId());

        ProfileEditor.ProfileEditorBuilder profileEditorBuilder = profile.toEditor();

        String profileImgUrl = profile.getProfileImgUrl(); // 기존 이미지 URL을 저장

        if (profileImg.isEmpty()) { // 프로필 이미지가 비어있다면
            if (!profileUpdateRequest.getChangeProfileImg()) { // 프로필 이미지를 안바꾼다고 하면, 이미지 안바꿈
//                response.setProfileImgUrl(profile.getProfileImgUrl());
                profileEditorBuilder.profileImgUrl(profileImgUrl); // 이미지 변경 없음
            } else { // 이미지 바꿈 - 파일 입력 안함
//                profile.setProfileImgUrl("");
//                profile.setKeyName("");
                profileEditorBuilder.profileImgUrl("").keyName("");
//                profileRepository.save(profile);
//                response.setProfileImgUrl(profile.getProfileImgUrl());
            }

        } else { // 이미지 바꿈 - 파일 입력

            if (!profileUpdateRequest.getChangeProfileImg()) {
                throw new ApiException(ErrorType.NULL_VALUE_EXIST);
            }

            // 프로필 이미지가 있는 경우, 삭제하고 진행
            s3Util.deleteFile(profile.getKeyName(), "upload/profile/");

            String originalImgName = profileImg.getOriginalFilename();
            String uniqueImgName = s3Util.generateUniqueImgName(originalImgName, member.getId());

            // 업로드할 파일을 시스템의 기본 임시 디렉토리에 저장
            String localFilePath = System.getProperty("java.io.tmpdir") + "/" + uniqueImgName;
            profileImg.transferTo(Paths.get(localFilePath));

            // S3 에 이미지 업로드
            s3Util.uploadFile(uniqueImgName, localFilePath, "upload/profile/");
            String imgUrl = "https://travel-planner-buckets.s3.ap-northeast-2.amazonaws.com/upload/profile/";
//            profile.setProfileImgUrl(imgUrl + uniqueImgName);
//            profile.setKeyName(uniqueImgName);
            profileEditorBuilder.profileImgUrl(imgUrl + uniqueImgName).keyName(uniqueImgName);

//            profileRepository.save(profile);

            // 로컬 임시 파일 삭제
            s3Util.deleteLocalFile(localFilePath);

//            response.setProfileImgUrl(profile.getProfileImgUrl());
        }

        ProfileEditor profileEditor = profileEditorBuilder.build();
        profile.edit(profileEditor);

        ProfileUpdateResponse response = ProfileUpdateResponse.builder()
                .userNickname(member.getUserNickname())
                .profileImgUrl(profile.getProfileImgUrl())
                .build();

        return response;
    }

    // 프로필 수정 [END] =========================================================================================
}