package server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.domain.user.domain.User;
import server.domain.user.dto.UserRequestDto;
import server.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    // 비밀번호 일치 확인
    public boolean signUpCheck(UserRequestDto request){
        String password = request.getPassword();
        String passwordCheck = request.getPasswordCheck();

        return password.equals(passwordCheck);
    }

    //회원가입
    public void signup (UserRequestDto request) {

        // 비밀번호 일치 확인
        if(!signUpCheck(request)) throw new IllegalArgumentException("입력하신 비밀번호가 일치하지 않습니다.");

        //회원 닉네임 중복 확인
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


}
