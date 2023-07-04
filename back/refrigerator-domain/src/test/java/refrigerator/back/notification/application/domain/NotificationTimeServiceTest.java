package refrigerator.back.notification.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.common.TimeService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTimeServiceTest {

    @Test
    @DisplayName("알림 시간 변환 서비스 테스트")
    void notificationTimeServiceTest() {
        NotificationTimeService service = new TimeService();

        LocalDateTime time = LocalDateTime.of(2023,1, 1, 0, 0, 0);

        assertThat(service.replace(time, time.plusSeconds(1))).isEqualTo("1 초 전");
        assertThat(service.replace(time, time.plusMinutes(1))).isEqualTo("1 분 전");
        assertThat(service.replace(time, time.plusHours(1))).isEqualTo("1 시간 전");
        assertThat(service.replace(time, time.plusDays(1))).isEqualTo("1 일 전");
        assertThat(service.replace(time, time.plusMonths(1))).isEqualTo("1 달 전");
        assertThat(service.replace(time, time.plusYears(1))).isEqualTo("1 년 전");
    }
}