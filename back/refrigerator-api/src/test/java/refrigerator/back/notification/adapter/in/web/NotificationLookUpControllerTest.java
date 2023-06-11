package refrigerator.back.notification.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;


import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.notification.application.port.in.CreateMemberNotificationUseCase;
import refrigerator.back.global.TestData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class NotificationLookUpControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired CreateTokenPort createTokenPort;
    @Autowired CreateMemberNotificationUseCase createMemberNotificationUseCase;
    @Autowired TestData testData;


    @Test
    void 알림_목록_조회() throws Exception {
        String memberId = testData.createMemberByEmail("email12344@gmail.com");
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 4000);
        createMemberNotificationUseCase.createMemberNotification(memberId);

        mockMvc.perform(get("/api/notifications?page=0")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isArray()
        ).andDo(print());
    }

    @Test
    void 알림_신호_조회() throws Exception {
        String memberId = testData.createMemberByEmail("email12344@gmail.com");
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 4000);
        createMemberNotificationUseCase.createMemberNotification(memberId);
        mockMvc.perform(get("/api/notifications/sign")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.sign").isBoolean()
        ).andDo(print());
    }

}