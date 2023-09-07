package travelplanner.project.demo.mail.dto;

import lombok.Getter;

@Getter
public class ChangePasswordDto {
    private String token;
    private String newPassWord;
}
