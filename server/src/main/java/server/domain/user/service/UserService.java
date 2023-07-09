package server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.user.domain.User;
import server.domain.user.dto.LoginRequest;
import server.domain.user.dto.SignUpRequest;
import server.domain.user.repository.UserRepository;
import server.global.code.ErrorCode;
import server.global.exception.HandlableException;
import server.global.security.jwt.JwtUtil;
import server.global.security.jwt.RefreshToken;
import server.global.security.jwt.RefreshTokenRepository;
import server.global.security.jwt.Token;
import server.global.util.GlobalRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    // 비밀번호 일치 확인
    public boolean signUpCheck(SignUpRequest request){
        String password = request.getPassword();
        String passwordCheck = request.getPasswordCheck();

        return password.equals(passwordCheck);
    }

    //회원가입
    public void signup (SignUpRequest request) {

        // 비밀번호 일치 확인
        if(!signUpCheck(request)) throw new IllegalArgumentException("입력하신 비밀번호가 일치하지 않습니다.");

        //회원 이메일 중복 확인
        Optional<User> find = userRepository.findByEmail(request.getEmail());
        if(find.isPresent()){
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");
        }

        // 비밀번호 Encoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = User.createUser(request);

        userRepository.saveAndFlush(user);
    }

    @Transactional
    public GlobalRequest login(LoginRequest loginRequest, HttpServletResponse response) {

        // 이메일 검사
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_USER));

        // 비밀번호 검사
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(user.getPassword(), loginRequest.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 이메일 정보로 토큰 생성
        Token token = jwtUtil.createAllToken(loginRequest.getEmail());

        // Refresh 토큰 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(loginRequest.getEmail());

        // 있다면 새토큰 발급후 업데이트
        // 없다면 새로 만들고 디비 저장
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(token.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(token.getRefreshToken(), loginRequest.getEmail());
            refreshTokenRepository.save(newToken);
        }

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response, token);

        return new GlobalRequest("Success Login", HttpStatus.OK.value());

    }

    private void setHeader(HttpServletResponse response, Token token) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, token.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, token.getRefreshToken());
    }
}
