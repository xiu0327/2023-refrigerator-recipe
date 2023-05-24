package refrigerator.back.authentication.adapter.out.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{

    private final RedisTemplate<String, String> stringRedisTemplate;

    public RefreshTokenRepositoryImpl(
            @Qualifier("tokenRedisTemplate") RedisTemplate<String, String> stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

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

    @Override
    public void removeData(String key){
        stringRedisTemplate.opsForValue().set(key, "", Duration.ofMillis(1));
    }
}
