package refrigerator.back.authentication.adapter.out.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.common.MakeRedisKey;

import java.time.Duration;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{

    private final RedisTemplate<String, String> stringRedisTemplate;
    private final String TOKEN_REDIS_KEY = "TOKEN";
    private final MakeRedisKey makeRedisKey;

    public RefreshTokenRepositoryImpl(
            @Qualifier("tokenRedisTemplate") RedisTemplate<String, String> stringRedisTemplate,
            MakeRedisKey makeRedisKey) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.makeRedisKey = makeRedisKey;
    }

    @Override
    public String getData(String key) {
        key = makeRedisKey.makeKey(TOKEN_REDIS_KEY, key);
        return stringRedisTemplate.opsForValue()
                .get(key);
    }

    @Override
    public void setData(String key, String value, long duration) {
        key = makeRedisKey.makeKey(TOKEN_REDIS_KEY, key);
        stringRedisTemplate.opsForValue()
                .set(key, value, Duration.ofMillis(duration));
    }

    @Override
    public void removeData(String key){
        key = makeRedisKey.makeKey(TOKEN_REDIS_KEY, key);
        stringRedisTemplate.opsForValue().set(key, "", Duration.ofMillis(1));
    }
}
