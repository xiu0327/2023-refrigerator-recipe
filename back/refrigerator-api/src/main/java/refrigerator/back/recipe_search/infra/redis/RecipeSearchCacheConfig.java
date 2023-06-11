package refrigerator.back.recipe_search.infra.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import refrigerator.back.recipe.infra.redis.config.RecipeCacheKey;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class RecipeSearchCacheConfig {

    @Bean
    public RedisCacheManager recipeNormalSearchCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration = getConfiguration();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(RecipeSearchCacheKey.RECIPE_NORMAL_SEARCH, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(RecipeSearchCacheKey.RECIPE_NORMAL_SEARCH_SEC)));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    @Bean
    public RedisCacheManager recipeFoodTypeCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration = getConfiguration();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(RecipeCacheKey.FOOD_TYPE, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(RecipeCacheKey.CONDITION_EXPIRE_SEC)));


        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    @Bean
    public RedisCacheManager recipeCategoryCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration = getConfiguration();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(RecipeCacheKey.CATEGORY, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(RecipeCacheKey.CONDITION_EXPIRE_SEC)));


        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }


    private RedisCacheConfiguration getConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(RecipeSearchCacheKey.RECIPE_NORMAL_SEARCH_SEC))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer()));
    }

}
