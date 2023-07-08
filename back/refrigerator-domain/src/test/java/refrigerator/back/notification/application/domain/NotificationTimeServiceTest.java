package refrigerator.back.notification.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.common.TimeService;
import refrigerator.back.global.time.TestCurrentTime;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTimeServiceTest {

    @Test
    @DisplayName("알림 시간 변환 서비스 테스트")
    void notificationTimeServiceTest() {
        NotificationTimeService service = new TimeService();

        LocalDateTime now = LocalDateTime.of(2023,1, 1, 0, 0, 0);

        assertThat(service.replace(now.minusSeconds(1), now)).isEqualTo("1 초 전");
        assertThat(service.replace(now.minusMinutes(1), now)).isEqualTo("1 분 전");
        assertThat(service.replace(now.minusHours(1), now)).isEqualTo("1 시간 전");
        assertThat(service.replace(now.minusDays(1), now)).isEqualTo("1 일 전");
        assertThat(service.replace(now.minusMonths(1), now)).isEqualTo("1 달 전");
        assertThat(service.replace(now.minusYears(1), now)).isEqualTo("1 년 전");
    }
}