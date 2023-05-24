package refrigerator.back.notification.adapter.out.persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.notification.adapter.out.NotificationStatusAdapter;

@SpringBootTest
class NotificationByMemberAdapterTest {

    @Autowired
    NotificationStatusAdapter notificationByMemberAdapter;

    @Test
    void 생성() {
        String memberId = "testEmail@gmail.com";
        notificationByMemberAdapter.create(memberId);
        Assertions.assertThat(notificationByMemberAdapter.getSign(memberId)).isFalse();
    }

    @Test
    void 수정() {
        String memberId = "testEmail@gmail.com";
        notificationByMemberAdapter.create(memberId);
        notificationByMemberAdapter.modify(memberId, true);
        Assertions.assertThat(notificationByMemberAdapter.getSign(memberId)).isTrue();
    }

}