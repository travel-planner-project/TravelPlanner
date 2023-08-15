package travelplanner.project.demo.global.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FCMInitializer {

    /*
    웹 애플리케이션을 시작할 때 firebase 에 앱을 등록해줘야 합니다.
    두번 등록하면 에러가 나므로, 시작할 때마다 초기화를 한번씩 해줘야합니다.
     */

    private static final Logger logger = LoggerFactory.getLogger(FCMInitializer.class);
    private static final String FIREBASE_CONFIG_PATH = "travel-planner-firebase-adminsdk.json";

    @PostConstruct
    public void initialize() {

        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    // 비밀키 가져와서 증명
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream()))
                            .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase application has benn initialized");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
