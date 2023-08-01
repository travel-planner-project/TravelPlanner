package travelplanner.project.demo.member.profile.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.member.profile.dto.request.PasswordCheckRequest;
import travelplanner.project.demo.member.profile.dto.request.PasswordUpdateRequest;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private MemberRepository memberRepository;
    private ProfileRepository profileRepository;
    private S3Service s3Service;
    private TokenUtil tokenUtil;
    private RedisUtil redisUtil;
    private PasswordEncoder encoder;


    // 비밀번호 체크
    public boolean checkUserPassword(PasswordCheckRequest request, HttpServletRequest httpRequest) throws Exception {

        // 헤더에서 JWT 토큰 추출
        String accessToken = tokenUtil.getJWTTokenFromHeader(httpRequest);

        // JWT 토큰을 이용하여 유저 정보를 추출
        String email = tokenUtil.getEmail(accessToken);

        Member member = memberRepository.findById(request.getUserId()).get();
        Member loginUser = memberRepository.findByEmail(email).get();

        String encodedPassword = loginUser.getPassword();

        if (encodedPassword.equals(member.getPassword())) {
            return true;

        } else {
            throw new Exception(ExceptionType.CHECK_PASSWORD_AGAIN);
        }
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(PasswordUpdateRequest request, HttpServletRequest httpRequest) {

        // 헤더에서 JWT 토큰 추출
        String accessToken = tokenUtil.getJWTTokenFromHeader(httpRequest);

        // JWT 토큰을 이용하여 유저 정보를 추출
        String email = tokenUtil.getEmail(accessToken);

        // 이메일을 기반으로 유저 정보를 찾는 로직 추가 (예를 들어, 데이터베이스에서 해당 이메일에 해당하는 유저 정보를 가져올 수 있음)
        Member member = memberRepository.findByEmail(email).get();

        if (member == null) {
            throw new Exception(ExceptionType.USER_NOT_FOUND);
        }

        // 로그인한 유저와 요청한 유저가 동일한지 확인
        boolean isCurrentUser = member.getId().equals(request.getUserId());

        if (!isCurrentUser) {
            throw new Exception(ExceptionType.THIS_USER_IS_NOT_SAME_LOGIN_USER);
        }

        String encodedPassword = encoder.encode(request.getPassword());
        member.setPassword(encodedPassword);

        memberRepository.save(member);
    }

    // 회원탈퇴
    @Transactional
    public void deleteUser(PasswordCheckRequest request, HttpServletRequest httpRequest) {

        // 헤더에서 JWT 토큰 추출
        String accessToken = tokenUtil.getJWTTokenFromHeader(httpRequest);

        // JWT 토큰을 이용하여 유저 정보를 추출
        String email = tokenUtil.getEmail(accessToken);

        // 이메일을 기반으로 유저 정보를 찾는 로직 추가 (예를 들어, 데이터베이스에서 해당 이메일에 해당하는 유저 정보를 가져올 수 있음)
        Member member = memberRepository.findByEmail(email).get();

        if (member == null) {
            throw new Exception(ExceptionType.USER_NOT_FOUND);
        }

        // 로그인한 유저와 요청한 유저가 동일한지 확인
        boolean isCurrentUser = member.getId().equals(request.getUserId());

        if (!isCurrentUser) {
            throw new Exception(ExceptionType.THIS_USER_IS_NOT_SAME_LOGIN_USER);
        }

        Profile profile = profileRepository.findProfileByMemberId(member.getId());

        // 프로필 이미지 삭제하기
        s3Service.deleteFile(profile.getKeyName());

        // 프로필 삭제
        profileRepository.delete(profile);

        // 레디스 삭제
        redisUtil.deleteData(member.getEmail());

        // 멤버 삭제
        memberRepository.delete(member);
    }
}
