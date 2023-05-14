package refrigerator.back.searchword.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class SearchWordRedisConfig {

    @Value("${spring.redis.search.host}")
    private String host;

    @Value("${spring.redis.search.port}")
    private int port;

    @Bean
    public RedisTemplate<String, String> searchWordRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(searchWordConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory searchWordConnectionFactory(){
        return new LettuceConnectionFactory(host, port);
    }

}
