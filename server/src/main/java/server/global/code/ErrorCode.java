package server.global.code;

public enum ErrorCode {

    // 에러 메세지
    NOT_EXISTS_PLANNER("존재 하지 않는 플래너 입니다.")
    , NOT_EXISTS_DATE("존재 하지 않는 날짜 입니다.")
    , NOT_EXISTS_TODO ("존재 하지 않는 투두 입니다.")
    , NOT_EXISTS_GROUP_MEMBER("존재 하지 않는 그룹멤버 입니다.")
    , NOT_EXISTS_USER("존재 하지 않는 유저 입니다.")
    , NOT_EXISTS_GROUP("존재 하지 않는 여행 그룹입니다.");




    public String msg;
    public String redirect;


    private ErrorCode(String msg) {
        this.msg = msg;
        this.redirect = "/";
    }
}
