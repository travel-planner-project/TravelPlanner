package server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import server.domain.user.detail.UserDetailsImpl;
import server.domain.user.domain.User;
import server.domain.user.dto.LoginRequestDto;
import server.domain.user.dto.UserRequestDto;
import server.domain.user.jwt.JwtTokenProvider;
import server.domain.user.service.UserService;
import server.domain.user.userRepository.UserRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;





    // 회원가입 2 (1이 안돼서 2로 진행해봄)
    @PostMapping("/signUp2")
    public User signUp2(@RequestBody User req){
        User userEntity = User.builder().email(req.getEmail())
                .password(req.getPassword())
                .passwordCheck(req.getPasswordCheck())
                .userNickName(req.getUserNickName())
                .build();
        return userService.insert(userEntity);
    }

    //회원가입 1
    @PostMapping("/signUp")
    public UserRequestDto signUp(@RequestBody UserRequestDto requestDto) {
        userService.signUp(requestDto);
        System.out.println(requestDto + "Controller");
        return requestDto;
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginrequestDto){
        User user = userRepository.findByEmail(loginrequestDto.getEmail()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 비밀번호 복호화(password Encoder 사용)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(user.getPassword(),loginrequestDto.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        System.out.println("로그인이 완료 되었습니다.");
        return jwtTokenProvider.createToken(user.getUserNickName());
    }

    // 로그인 후 token이 생성되면 해당 토큰을 이용해 유저네임출력
    @GetMapping("/userInfo")
    @ResponseBody
    public String getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        if(userDetailsImpl != null){
            System.out.println("로그인 완료");
            return userDetailsImpl.getUser().getUserNickName();
        }

        return "토큰생성 완료!";
    }


}
