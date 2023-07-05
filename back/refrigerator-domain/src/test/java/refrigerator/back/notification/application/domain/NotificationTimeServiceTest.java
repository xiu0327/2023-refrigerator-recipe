package refrigerator.back.notification.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.time.TimeService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class NotificationTimeServiceTest {

    @Test
    @DisplayName("알림 시간 변환 서비스 테스트")
    void notificationTimeServiceTest() {
        NotificationTimeService service = new TimeService(
                () -> LocalDateTime.of(2023, 2, 1, 0, 0, 0)
        );
        LocalDateTime time = LocalDateTime.of(2023,1, 1, 0, 0, 0);

        assertThat(service.replace(time)).isEqualTo("1 달 전");
    }
}