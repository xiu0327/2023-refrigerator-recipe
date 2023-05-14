package refrigerator.back.identification.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import refrigerator.back.identification.application.port.out.IdentificationRedisPort;

import java.time.Duration;

@Component
public class IdentificationAdapter implements IdentificationRedisPort {

    private final RedisTemplate<String, String> redisTemplate;

    public IdentificationAdapter(
            @Qualifier("identificationRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getData(String key){
        ValueOperations<String, String> result = redisTemplate.opsForValue();
        return result.get(key);
    }

    @Override
    public void setData(String key, String value, long duration){
        ValueOperations<String, String> result = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofMillis(duration);
        result.set(key, value, expireDuration);
    }

    @Override
    public void deleteData(String key){
        redisTemplate.delete(key);
    }
}
