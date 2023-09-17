package travelplanner.project.demo.domain.auth.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangePasswordDto {
    private String newPassword;
}
