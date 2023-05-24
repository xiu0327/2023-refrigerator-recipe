package refrigerator.back.identification.infra.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class IdentificationRedisConfig {

    @Value("${spring.redis.identification.host}")
    private String host;

    @Value("${spring.redis.identification.port}")
    private int port;

    @Bean
    public RedisTemplate<String, String> identificationRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(identificationRedisConnectionFactory());
        redisTemplate.setKeySerializer(new IdentificationPrefixedKeySerializer());
        redisTemplate.setHashKeySerializer(new IdentificationPrefixedKeySerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory identificationRedisConnectionFactory(){
        return new LettuceConnectionFactory(host, port);
    }
}
