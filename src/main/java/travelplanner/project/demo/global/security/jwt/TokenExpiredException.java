package travelplanner.project.demo.global.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException() {
        super("Token expired");
    }
}

