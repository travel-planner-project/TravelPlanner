package travelplanner.project.demo.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final MemberRepository memberRepository;
    private final TokenUtil tokenUtil;
    private final RedisUtil redisUtil;

    // 임시 토큰 생성
    public String generateTempToken(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        String tempToken = tokenUtil.generateTempToken(member);  // TokenUtil에 임시 토큰 생성 메서드 추가
        redisUtil.setDataExpireWithPrefix("temp", email, tempToken, Duration.ofMinutes(30));

        return tempToken;
    }
}
