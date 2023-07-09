package server.domain.user.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.domain.user.dto.LoginRequest;
import server.domain.user.dto.SignUpRequest;
import server.domain.user.service.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

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
    public void login(@RequestBody LoginRequest request, HttpServletResponse response){
        userService.login(request, response);
        System.out.println("Login Success");
    }
}