package server.global.code;

public enum ErrorCode {

    // 에러 메세지
    NOT_EXISTS_PLANNER("존재 하지 않는 플래너 입니다.");




    public String msg;
    public String redirect;


    private ErrorCode(String msg) {
        this.msg = msg;
        this.redirect = "/";
    }
}