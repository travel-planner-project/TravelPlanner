package travelplanner.project.demo.global.exception.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import travelplanner.project.demo.global.exception.ErrorType;

import java.util.Map;

@Controller
public class WebSocketErrorController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /***
     * 모든 엔드포인트에 대하여 공통된 에러 컨트롤러를 사용하기 위해
     * MessageMapping을 "/"로 처리하였습니다.
     *
     * 웹소켓 에러는 RestApi 에러 분기 처리와 별도로 처리해야 해서
     * 기존에 있던 ApiException으로 서버에서 예외처리를하고
     * 웹소켓 에러 메세지는 별도로 내려주기 위해 웹소켓 에러 컨트롤러를 작성하였습니다.
     *
     * 이후에 리팩토링해야할 사항이 있으면 논의 후 수정하겠습니다.
     *
     */
    @MessageMapping("/")
    public void handleChatMessage(ErrorType errorType){

        // 전달받은 에러 코드를 프론트로 전송하기 위한 부분
        messagingTemplate.convertAndSend("/sub/error/planner-message" ,
                Map.of("code", errorType.getErrorCode(), "message", errorType.getMessage(),
                "status", errorType.getStatus()
                )
        );
    }
}
