package travelplanner.project.demo.global.exception.WebSocket;

import travelplanner.project.demo.global.exception.ErrorType;

public class WebSocketException extends RuntimeException{

    private final ErrorType errorType;
    public WebSocketException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType(){return errorType;}
}
