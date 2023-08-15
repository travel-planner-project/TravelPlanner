package travelplanner.project.demo.global.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.member.Member;

@RestController
@RequiredArgsConstructor
public class NotificationController {


    private final NotificationService notificationService;
    private final AuthUtil authUtil;

    // 사용자가 로그인 된 후 firebase 에게 전달받은 token 값을 웹서버에 등록
    @PostMapping("/firebase/create")
    public ResponseEntity createToken(@RequestBody String token) {

        Member member = authUtil.getCurrentMember();
        notificationService.createToken(member.getId(), token);

        return ResponseEntity.ok().build();
    }

    // 사용자가 로그아웃하면 토큰 삭제
    @PostMapping("/firebase/remove")
    public ResponseEntity removeToken(@RequestBody String token) {

        Member member = authUtil.getCurrentMember();
        notificationService.deleteToken(member.getId(), token);

        return ResponseEntity.ok().build();
    }
}
