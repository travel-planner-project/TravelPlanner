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
public class StompErrorHandler extends StompSubProtocolErrorHandler {
    public StompErrorHandler(){
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        Throwable exception = ex;
        System.out.println("--------------ex:"+ex);
        System.out.println("--------------ex:"+clientMessage);
        if(exception instanceof MalformedInputException){
            log.info("잘못된 양식에 의한 에러");
            return handleUnauthorizedException(clientMessage, exception);
        }
        /*if(exception instanceof TokenExpiredException){
            log.info("토큰 만료 익셉션");
            return handleUnauthorizedException(clientMessage, exception);
        }*/
        if (exception instanceof MessageDeliveryException)
        {
            log.info("예외 = {}", exception.getMessage());
            log.info("세부내용 = {}", exception.getCause().getMessage());


            return handleUnauthorizedException(clientMessage, exception);
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> handleUnauthorizedException(Message<byte[]> clientMessage, Throwable ex)
    {
        ErrorType errorType;

        if (ex.equals("Token expired")){
            errorType = ErrorType.ACCESS_TOKEN_EXPIRED;
        }else{
            errorType = ErrorType.INVALID_MESSAGE_FORMAT;
        }

        return prepareErrorMessage(clientMessage, errorType, String.valueOf(errorType));

    }

    private Message<byte[]> prepareErrorMessage(Message<byte[]> clientMessage, ErrorType apiError, String errorCode)
    {

        String message = apiError.getMessage();

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(errorCode);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(message.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }

    public Message<byte[]> errorMessage(ErrorType errorType)
    {

        String message = errorType.getMessage();

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(String.valueOf(errorType));
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(message.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
