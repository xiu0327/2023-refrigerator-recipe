package refrigerator.back.notification.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
@EnableCaching
@EnableRedisRepositories
public class NotificationRedisConfig {
    @Value("${spring.redis.notification.host}")
    private String host;

    @Value("${spring.redis.notification.port}")
    private int port;

    @Value("${spring.redis.notification.password}")
    private String password;

    @Bean
    public RedisTemplate<String, Boolean> notificationRedisTemplate(){
        RedisTemplate<String, Boolean> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(notificationConnectionFactory());
        redisTemplate.setKeySerializer(new NotificationPrefixedKeySerializer());
        redisTemplate.setHashKeySerializer(new NotificationPrefixedKeySerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory notificationConnectionFactory(){
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(host);
        redisConfiguration.setPort(port);
        redisConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisConfiguration);
    }

}
