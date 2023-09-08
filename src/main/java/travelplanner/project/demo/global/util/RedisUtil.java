package travelplanner.project.demo.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;

    // 레디스에서 특정 키를 가진 값 얻기
    public String getData(String key) {

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }


    // 키:값 저장 시 만료기한 설정
    public void setDataExpire(String key, String value, Duration duration) {

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value, duration);
    }


    // 레디스에서 특정 키를 가진 값 삭제
    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }

    // 임시 토큰을 리프레시 토큰과 충돌이 일어나지 않게하기 위해 키를 하나 더 받습니다.
    public void setDataExpireWithPrefix(String prefix, String key, String value, Duration duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String fullKey = prefix + ":" + key;
        valueOperations.set(fullKey, value, duration);
    }

}
