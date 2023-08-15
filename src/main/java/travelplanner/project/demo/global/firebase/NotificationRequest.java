package travelplanner.project.demo.global.firebase;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class NotificationRequest {

    private String title;
    private String message;
    private String token;
//    private String imgUrl;

    @Builder
    public NotificationRequest (String title, String message, String token) {

        this.title = title;
        this.message = message;
        this.token = token;
//        this.imgUrl = imgUrl;
    }
}
