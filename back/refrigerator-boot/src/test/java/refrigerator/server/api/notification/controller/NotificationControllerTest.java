package refrigerator.server.api.notification.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NotificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("알림 목록 조회")
    @WithUserDetails("jktest101@gmail.com")
    void getNotificationListTest() throws Exception {

        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        Notification.NotificationBuilder builder = Notification.builder()
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .createDate(now)
                .memberId("jktest101@gmail.com")
                .method(BasicHttpMethod.GET.name());

        em.persist(builder.message("test message1").build());
        em.persist(builder.message("test message2").build());
        em.persist(builder.message("test message3").build());

        mockMvc.perform(
                get("/api/notifications?page=0")
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isArray()
        ).andDo(print());
    }

    @Test
    @DisplayName("알림 단건 조회 => 읽음 상태 true로 변경")
    @WithUserDetails("jktest101@gmail.com")
    void readNotificationTest() throws Exception {

        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        Notification notification = Notification.builder()
                .message("test message")
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .createDate(now)
                .memberId("jktest101@gmail.com")
                .method(BasicHttpMethod.GET.name())
                .build();

        Long id = em.persistAndGetId(notification, Long.class);

        mockMvc.perform(
                get("/api/notifications/" + id)
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.readStatus").isBoolean()
        ).andDo(print());
    }

}