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

    // 사용자가 로그인 된 후 firebase 에게 전달받은 token 값을 웹서버에 등록
    private final NotificationService notificationService;
    private final AuthUtil authUtil;

    @PostMapping("/firebase/register")
    public ResponseEntity register(@RequestBody String token) {

        Member member = authUtil.getCurrentMember();
        notificationService.register(member.getId(), token);
        return ResponseEntity.ok().build();
    }
}
