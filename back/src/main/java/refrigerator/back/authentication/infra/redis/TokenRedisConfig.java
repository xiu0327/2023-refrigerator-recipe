package refrigerator.back.authentication.infra.redis;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class TokenRedisConfig {

    @Value("${spring.redis.token.host}")
    private String host;
    @Value("${spring.redis.token.port}")
    private int port;

    @Bean
    public RedisTemplate<String, String> tokenRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(tokenRedisConnectionFactory());
        redisTemplate.setKeySerializer(new TokenPrefixedKeySerializer());
        redisTemplate.setHashKeySerializer(new TokenPrefixedKeySerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory tokenRedisConnectionFactory(){
        return new LettuceConnectionFactory(host, port);
    }
}
