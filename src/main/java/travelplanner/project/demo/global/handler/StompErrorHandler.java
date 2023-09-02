package travelplanner.project.demo.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.exception.TokenExpiredException;

import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class  StompErrorHandler extends StompSubProtocolErrorHandler {
    public StompErrorHandler(){
        super();
    }

    /***
     *
     * 웹소켓 에러 핸들러
     * 웹소켓 통신 엔드포인트의 에러를 처리합니다.
     * 토큰 만료로 인한 메세지 전송 실패 및 메시지 인코딩 오류 등
     * 테스트 도중 새로운 에러가 발견된다면 추후 리팩토링 에정입니다.
     * 
     */

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        Throwable exception = ex;

        if(exception instanceof MalformedInputException){
            log.info("잘못된 양식에 의한 에러");
            return handleUnauthorizedException(clientMessage, exception);
        }

        if (exception instanceof MessageDeliveryException)
        {
            // 메세지 전송 도중 토큰 만료
            return handleUnauthorizedException(clientMessage, exception);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> handleUnauthorizedException(Message<byte[]> clientMessage, Throwable ex)
    {
        ErrorType errorType;

        if (ex.getCause().getMessage().equals("Token expired")){
            errorType = ErrorType.ACCESS_TOKEN_EXPIRED;
        }else{
            errorType = ErrorType.INVALID_MESSAGE_FORMAT;
        }

        return prepareErrorMessage(clientMessage, errorType, String.valueOf(errorType));

    }


    /***
     *
     * 웹소켓 에러 메세지를 프론트로 내려주는 코드
     * StompCommand 형식을 error로 지정
     * 에러 코드, 메세지, 상태를 내려줍니다.
     *
     */
    private Message<byte[]> prepareErrorMessage(Message<byte[]> clientMessage, ErrorType apiError, String errorCode)
    {

        String message = apiError.getMessage();

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(errorCode);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(message.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }

}
