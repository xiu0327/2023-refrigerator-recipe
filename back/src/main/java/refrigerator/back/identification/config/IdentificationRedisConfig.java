package refrigerator.back.identification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

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
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory identificationRedisConnectionFactory(){
        return new LettuceConnectionFactory(host, port);
    }
}
