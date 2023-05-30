package refrigerator.back.authentication.infra.redis;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class TokenRedisConfig {

    @Value("${spring.redis.token.host}")
    private String host;
    @Value("${spring.redis.token.port}")
    private int port;

    @Value("${spring.redis.token.password}")
    private String password;


    @Bean
    public RedisTemplate<String, String> tokenRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(tokenRedisConnectionFactory());
        redisTemplate.setKeySerializer(new TokenPrefixedKeySerializer());
        redisTemplate.setHashKeySerializer(new TokenPrefixedKeySerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
    @Bean
    public RedisConnectionFactory tokenRedisConnectionFactory(){
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(host);
        redisConfiguration.setPort(port);
        redisConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisConfiguration);
    }
}
