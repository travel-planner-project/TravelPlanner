package travelplanner.project.demo.global.exception;

import lombok.Getter;

@Getter
public class Exception extends RuntimeException{

    private ExceptionType error;

    public Exception(ExceptionType e) {

        super(e.getMessage());
        this.error = e;
    }
}
