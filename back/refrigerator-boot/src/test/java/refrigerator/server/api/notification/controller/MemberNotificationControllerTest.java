package refrigerator.server.api.notification.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class MemberNotificationControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("알림 신호 조회 => 종 버튼에 빨간점이 있는지 없는지 확인")
    @WithUserDetails("jktest101@gmail.com")
    void getNotificationSignTest() throws Exception {

        mockMvc.perform(
                get("/api/notifications/sign")
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.sign").isBoolean()
        ).andDo(print());
    }

    @Test
    @DisplayName("알림 신호 끄기 => 종 버튼 눌러서 빨간 점 삭제")
    @WithUserDetails("jktest101@gmail.com")
    void TurnOffNotificationSignTest() throws Exception {

        mockMvc.perform(
                get("/api/notification/sign")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}