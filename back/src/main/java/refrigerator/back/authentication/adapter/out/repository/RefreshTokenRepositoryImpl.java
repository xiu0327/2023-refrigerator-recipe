package refrigerator.back.authentication.adapter.out.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public String getData(String key) {
        return stringRedisTemplate.opsForValue()
                .get(key);
    }

    @Override
    public void setData(String key, String value, long duration) {
        stringRedisTemplate.opsForValue()
                .set(key, value, Duration.ofMillis(duration));
    }
}
