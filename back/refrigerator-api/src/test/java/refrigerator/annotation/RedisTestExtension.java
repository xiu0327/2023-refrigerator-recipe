package refrigerator.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
public class RedisTestExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        Class<?> testClass = context.getTestClass().orElse(null);
        if (testClass != null){
            RedisFlushAll redisRollback = AnnotationUtils.findAnnotation(testClass, RedisFlushAll.class);
            if (redisRollback != null){
                String beanName = redisRollback.beanName();
                RedisTemplate redisTemplate = getRedisTemplate(context, beanName);
                redisExecute(redisTemplate);
                log.info("Redis execute flush all by {}", beanName);
            }
        }
    }

    private RedisTemplate getRedisTemplate(ExtensionContext context, String beanName) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        return applicationContext.getBean(beanName, RedisTemplate.class);
    }

    private void redisExecute(RedisTemplate redisTemplate) {
        redisTemplate.execute((RedisCallback<? extends Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }

}
