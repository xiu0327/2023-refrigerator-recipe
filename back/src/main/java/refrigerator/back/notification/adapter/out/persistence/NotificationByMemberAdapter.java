package refrigerator.back.notification.adapter.out.persistence;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.common.MakeRedisKey;
import refrigerator.back.notification.application.port.out.read.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.write.ModifyMemberNotificationPort;
import refrigerator.back.notification.application.port.out.write.CreateMemberNotificationPort;

@Repository
public class NotificationByMemberAdapter implements CreateMemberNotificationPort, ModifyMemberNotificationPort, FindMemberNotificationSignPort {

    private final RedisTemplate<String, Boolean> repository;
    private final String NOTIFICATION_REDIS_KEY = "NOTIFICATION";
    private final MakeRedisKey makeRedisKey;

    public NotificationByMemberAdapter(
            @Qualifier("notificationRedisTemplate") RedisTemplate<String, Boolean> repository,
            MakeRedisKey makeRedisKey) {
        this.repository = repository;
        this.makeRedisKey = makeRedisKey;
    }

    @Override
    public void create(String memberId) {
        String key = makeRedisKey.makeKey(
                NOTIFICATION_REDIS_KEY,
                memberId);
        repository.opsForValue().set(key,
                false);
    }

    @Override
    public void modify(String memberId, boolean value) {
        String key = makeRedisKey.makeKey(NOTIFICATION_REDIS_KEY, memberId);
        repository.opsForValue().set(
                key, value);
    }

    @Override
    public Boolean getSign(String memberId) {
        String key = makeRedisKey.makeKey(NOTIFICATION_REDIS_KEY, memberId);
        return repository.opsForValue().get(key);
    }
}