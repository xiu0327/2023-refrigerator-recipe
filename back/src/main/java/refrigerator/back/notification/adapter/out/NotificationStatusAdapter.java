package refrigerator.back.notification.adapter.out;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.common.MakeRedisKey;
import refrigerator.back.notification.application.port.out.read.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.write.ModifyMemberNotificationPort;
import refrigerator.back.notification.application.port.out.write.CreateMemberNotificationPort;

@Repository
public class NotificationStatusAdapter implements CreateMemberNotificationPort, ModifyMemberNotificationPort, FindMemberNotificationSignPort {

    private final RedisTemplate<String, Boolean> repository;

    public NotificationStatusAdapter(
            @Qualifier("notificationRedisTemplate") RedisTemplate<String, Boolean> repository) {
        this.repository = repository;
    }

    @Override
    public void create(String memberId) {
        repository.opsForValue().set(memberId, false);
    }

    @Override
    public void modify(String memberId, boolean value) {
        repository.opsForValue().set(memberId, value);
    }

    @Override
    public Boolean getSign(String memberId) {
        return repository.opsForValue().get(memberId);
    }
}