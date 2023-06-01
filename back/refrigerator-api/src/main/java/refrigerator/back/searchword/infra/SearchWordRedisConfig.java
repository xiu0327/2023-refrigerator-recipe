package refrigerator.back.searchword.infra;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class SearchWordRedisConfig {

    @Value("${spring.redis.search.host}")
    private String host;

    @Value("${spring.redis.search.port}")
    private int port;

    private final RedisCacheConfiguration configuration;

    @Bean
    public RedisTemplate<String, String> searchWordRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(searchWordConnectionFactory());
        redisTemplate.setKeySerializer(new SearchWordPrefixedKeySerializer());
        redisTemplate.setHashKeySerializer(new SearchWordPrefixedKeySerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager searchWordsCacheManager(RedisConnectionFactory redisConnectionFactory){

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(SearchWordRedisKey.RECOMMEND_SEARCH_WORD, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(SearchWordRedisKey.RECOMMEND_SEARCH_WORD_TTL)));
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }
    @Bean
    public RedisConnectionFactory searchWordConnectionFactory(){
        return new LettuceConnectionFactory(host, port);
    }

}
