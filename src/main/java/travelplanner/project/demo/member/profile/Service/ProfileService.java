package travelplanner.project.demo.member.profile.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.MemberService;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.member.profile.dto.response.ProfileResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final MemberService memberService;
    private final S3Service s3Service;


    // 특정 유저의 프로필 찾기
    @Transactional
    public ProfileResponse findUserProfile (Long userId) throws Exception {

        Profile profile = profileRepository.findProfileByMemberUserId(userId);

        if (profile == null) { // 특정 유저의 프로필이 없는경우

            // 프로필 추가
            Member member = memberRepository.findByUserId(userId).get();

            profile = Profile.builder()
                    .member(member)
                    .build();

            profileRepository.save(profile);
        }

        // 로그인한 유저 찾기
        Long loginUserId = memberRepository.findByEmail(memberService.findLoginUser()).get().getUserId();
        Boolean booleanValue = Objects.equals(loginUserId, userId);

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setProfileImgUrl(profile.getProfileImgUrl());
        profileResponse.setEmail(profile.getMember().getEmail());
        profileResponse.setUserNickname(profile.getMember().getUserNickname());
        profileResponse.setCheckUser(booleanValue);

        return profileResponse;
    }

    // 프로필 수정 [START] =========================================================================================
    @Transactional
    public void updateUserProfileImg(Long userId, MultipartFile profileImg, String userNickname) throws Exception, IOException {

        // 해당 멤버 존재하는지 확인
        memberService.findMember(userId);

        // 로그인한 사람과 프로필 유저가 사람이 같은지 확인
        Long loginUserId = memberRepository.findByEmail(memberService.findLoginUser()).get().getUserId();
        memberService.checkMember(userId, loginUserId);

        Profile profile = profileRepository.findProfileByMemberUserId(userId);
        Member member = memberRepository.findByUserId(userId).get();
        member.setUserNickname(userNickname);

        memberRepository.save(member);

        // 프로필 이미지가 있는 경우, 삭제하고 진행
        s3Service.deleteFile(profile.getKeyName());

        String originalImgName = profileImg.getOriginalFilename();
        String uniqueImgName = generateUniqueImgName(originalImgName, loginUserId);

        // 업로드할 파일을 시스템의 기본 임시 디렉토리에 저장
        String localFilePath = System.getProperty("java.io.tmpdir") + uniqueImgName;
        profileImg.transferTo(Paths.get(localFilePath));

        // S3 에 이미지 업로드
        s3Service.uploadFile(uniqueImgName, localFilePath);
        String imgUrl = "https://travel-planner-buckets.s3.ap-northeast-2.amazonaws.com/upload/profile";
        profile.setProfileImgUrl(imgUrl + uniqueImgName);
        profile.setKeyName(uniqueImgName);
        profileRepository.save(profile);

        // 로컬 임시 파일 삭제
        deleteLocalFile(localFilePath);
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
