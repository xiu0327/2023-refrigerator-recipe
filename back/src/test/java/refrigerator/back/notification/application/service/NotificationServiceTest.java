package refrigerator.back.notification.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.notification.application.domain.Notifications;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest
// @Transactional
class NotificationServiceTest {

    @Test
    public void DateTest() {

        Notifications notifications = Notifications.create("test.png", "안녕하세요 ^^;; (웃음웃음)",
                "기타", "localhost:8080/어쩌고저쩌고~~", "post", "ehgus5825@naver.com");

        notifications.setCreateTime(LocalDateTime.of(2023, 4, 5, 22, 29, 50));

        System.out.println("notifications.getRegisterTime() = " + notifications.getRegisterTime());

    }

}