package refrigerator.back.notification.adapter.persistence;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.application.port.out.memberNotification.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.memberNotification.CreateMemberNotificationPort;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;

import java.util.Optional;


@Repository
public class MemberNotificationAdapter implements CreateMemberNotificationPort, ModifyMemberNotificationPort, FindMemberNotificationSignPort {

    private final RedisTemplate<String, Boolean> repository;

    public MemberNotificationAdapter(
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
    public Optional<Boolean> getSign(String memberId) {
        return Optional.ofNullable(repository.opsForValue().get(memberId));
    }
}