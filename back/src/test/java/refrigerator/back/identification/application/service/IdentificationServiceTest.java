package refrigerator.back.identification.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.identification.exception.IdentificationExceptionType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class IdentificationServiceTest {

    @Autowired IdentificationService identificationService;
    private Long testDuration = 30L;

    @Test
    void 인증_번호_통합테스트() throws InterruptedException {
        String email = "codinging0326@gmail.com";
        String code = identificationService.sendAuthenticationNumber(email, 1000L);
        Thread.sleep(500);
        Boolean result = identificationService.checkAuthenticationNumber(code, email);
        assertThat(result).isTrue();
    }

    @Test
    void 인증_번호_유효시간_만료() throws InterruptedException {
        String email = "codinging0326@gmail.com";
        String code = identificationService.sendAuthenticationNumber(email, testDuration);
        Thread.sleep(50);
        assertThrows(BusinessException.class, () -> {
            try{
                identificationService.checkAuthenticationNumber(code, email);
            } catch(BusinessException e){
                log.info(e.getMessage());
                assertThat(e.getBasicExceptionType()).isEqualTo(IdentificationExceptionType.TIME_OUT_CODE);
                throw e;
            }
        });
    }
}