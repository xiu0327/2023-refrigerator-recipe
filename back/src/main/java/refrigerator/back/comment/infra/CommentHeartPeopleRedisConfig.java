package refrigerator.back.comment.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class CommentHeartPeopleRedisConfig {

    @Value("${spring.redis.comment.host}")
    private String host;

    @Value("${spring.redis.comment.port}")
    private int port;

    @Bean
    public RedisTemplate<String, String> commentHeartPeopleRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(commentHeartPeopleRedisFactory());
        redisTemplate.setKeySerializer(new CommentPrefixedKeySerializer());
        redisTemplate.setHashKeySerializer(new CommentPrefixedKeySerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory commentHeartPeopleRedisFactory(){
        return new LettuceConnectionFactory(host, port);
    }

}
