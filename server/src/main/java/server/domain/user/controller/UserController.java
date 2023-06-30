package server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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


    // 회원가입
    @PostMapping("/register")
    public void signUp2(@RequestBody UserRequestDto request){

        userService.signup(request);

        System.out.println("회원가입 request: " + request);
    }

    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto loginrequestDto){
//        User user = userRepository.findByEmail(loginrequestDto.getEmail()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
//
//        // 비밀번호 복호화(password Encoder 사용)
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        if(passwordEncoder.matches(user.getPassword(),loginrequestDto.getPassword())){
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//        System.out.println("로그인이 완료 되었습니다.");
//        return jwtTokenProvider.createToken(user.getUserNickName());
//    }

//    // 로그인 후 token이 생성되면 해당 토큰을 이용해 유저네임출력
//    @GetMapping("/userInfo")
//    @ResponseBody
//    public String getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
//        if(userDetailsImpl != null){
//            System.out.println("로그인 완료");
//            return userDetailsImpl.getUser().getUserNickName();
//        }
//
//        return "토큰생성 완료!";
//    }
}
