package server.global.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalRequest {

    private String msg;
    private int statusCode;

    public GlobalRequest (String msg, int statusCode) {

        this.msg = msg;
        this.statusCode = statusCode;
    }
}
