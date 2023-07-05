package server.domain.user.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import server.domain.user.domain.User;
import server.domain.user.dto.LoginRequest;
import server.domain.user.dto.SignUpRequest;
import server.domain.user.dto.TokenResponse;
import server.global.security.jwt.JwtTokenProvider;
import server.domain.user.service.UserService;
import server.domain.user.repository.UserRepository;
import server.global.security.jwt.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;


    @ApiOperation(
            value = "회원가입"
            , notes = "이메일, 닉네임, 비밀번호, 비밀번호 확인을 통해 회원 가입"
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "SIGN-IN SUCCESS")
                    , @ApiResponse(code = 400, message = "BAD REQUEST: PLEASE CHECK INPUT DATA")
                    , @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
            }
    )
    @PostMapping("/register")
    public void signUp(@RequestBody SignUpRequest request){

        userService.signup(request);
    }



    @ApiOperation(
            value = "로그인"
            , notes = "이메일, 비밀번호를 통해 로그인"
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "LOGIN SUCCESS")
                    , @ApiResponse(code = 400, message = "BAD REQUEST: PLEASE CHECK INPUT DATA")
                    , @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
            }
    )
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 비밀번호 복호화(password Encoder 사용)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(user.getPassword(),request.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        System.out.println("로그인이 완료 되었습니다.");
        return jwtTokenProvider.createToken(user.getUserNickname());
    }

    // 로그인 후 token이 생성되면 해당 토큰을 이용해 유저네임출력
    @GetMapping("/info")
    @ResponseBody
    public String getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        if(userDetailsImpl != null){
            System.out.println("로그인 완료");
            return userDetailsImpl.getUser().getUserNickname();
        }

        return "토큰생성 완료!";
    }
}
