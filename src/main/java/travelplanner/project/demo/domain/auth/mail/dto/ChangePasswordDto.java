package travelplanner.project.demo.domain.auth.mail.dto;

import lombok.Getter;

@Getter
public class ChangePasswordDto {
    private String token;
    private String newPassWord;
}
