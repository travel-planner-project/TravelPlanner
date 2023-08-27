package travelplanner.project.demo.member.socialauth.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth {

    private final String GOOGLE_LOGIN_URL = "https://accounts.google.com/o/oauth2/auth";
    private final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUrl;


    // redirectUrl
    public String getGoogleRedirectUrl() {
        return GOOGLE_LOGIN_URL + "?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl + "&response_type=code&scope=email%20profile%20openid";
    }


    // accessToken 요청하기
    public ResponseEntity<String> requestAccessToken(String code) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }

        return null;
    }


    // accessToken 가져오기
    public GoogleOAuthTokenResponse getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {

        log.info("response.getBody(): " + response.getBody());
        return objectMapper.readValue(response.getBody(), GoogleOAuthTokenResponse.class);
    }


    // 유저 정보 요청 하기
    public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenResponse oAuthTokenDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthTokenDto.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);
        log.info("response.getBody(): " + response.getBody());
        return response;
    }


    // 유저 정보 꺼내기
    public GoogleUserInfoDto getUserInfo(ResponseEntity<String> response) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getBody(), GoogleUserInfoDto.class);
    }
}

