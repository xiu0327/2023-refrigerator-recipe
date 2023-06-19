package refrigerator.back.identification.adapter.persistence;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
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
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setData(String key, String value, long duration){
        Duration expireDuration = Duration.ofMillis(duration);
        redisTemplate.opsForValue().set(
                key,
                value,
                expireDuration);
    }
    @Override
    public void deleteData(String key){
        redisTemplate.delete(key);
    }

}
