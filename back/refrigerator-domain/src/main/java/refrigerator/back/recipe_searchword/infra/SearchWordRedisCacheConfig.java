package refrigerator.back.recipe_searchword.infra;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import refrigerator.back.global.config.redis.SearchRedisLettuceConnectionConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class SearchWordRedisCacheConfig {
    private final RedisCacheConfiguration configuration;

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

}
