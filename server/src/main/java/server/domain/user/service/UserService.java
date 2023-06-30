package server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.domain.user.domain.User;
import server.domain.user.dto.UserRequestDto;
import server.domain.user.userRepository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public User insert(User user){
        return userRepository.save(user);
    }


    // 비밀번호 일치 확인
    public boolean signUpCheck(UserRequestDto userRequestDto){
        String password = userRequestDto.getPassword();
        String passwordCheck = userRequestDto.getPasswordCheck();

        return password.equals(passwordCheck);
    }

    //회원가입
    public UserRequestDto signUp(UserRequestDto requestDto){
        // 비밀번호 일치 확인
        if(!signUpCheck(requestDto)) throw new IllegalArgumentException("입력하신 비밀번호가 일치하지 않습니다.");

        //회원 닉네임 중복 확인
        Optional<User> find = userRepository.findByUserNickName(requestDto.getUserNickName());
        if(find.isPresent()){
            throw new IllegalArgumentException("이미 등록된 닉네임 입니다!");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = new User();
        userRepository.save(user);
        System.out.println(requestDto + "service");
        return requestDto;
    }
}
