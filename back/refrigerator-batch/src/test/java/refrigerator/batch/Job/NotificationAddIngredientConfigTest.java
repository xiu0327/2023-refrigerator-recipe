package refrigerator.batch.Job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.outbound.repository.jpa.NotificationPersistenceRepository;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "job.name=updateIngredientBatch_Job")
@Slf4j
class NotificationAddIngredientConfigTest extends BatchTestSupport {

    @Autowired
    MemberNotificationPersistenceRepository memberNotificationPersistenceRepository;

    @Autowired
    NotificationPersistenceRepository notificationPersistenceRepository;

    @AfterEach
    void after(){
        notificationPersistenceRepository.deleteAll();
        memberNotificationPersistenceRepository.deleteAll();
    }

    @Test
    @DisplayName("식재료 추가 알림 생성 테스트")
    void addNotificationByUpdatedIngredient() throws Exception {

        // given
        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        String email = "email123@gmail.com";
        String name = "감자";

        entityManager.getTransaction().begin();

        Member member = Member.builder()
                .email(email)
                .memberStatus(MemberStatus.STEADY_STATUS)
                .joinDateTime(now)
                .nickname("-")
                .password("****")
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .build();

        save(member);

        SuggestedIngredient suggestedIngredient = SuggestedIngredient.builder()
                .name(name)
                .unit("g")
                .email(email)
                .build();

        save(suggestedIngredient);

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(email)
                .sign(false)
                .build();

        MemberNotification mn = memberNotificationPersistenceRepository.save(memberNotification);

        entityManager.getTransaction().commit();

        assertThat(member.getId()).isNotNull();
        assertThat(suggestedIngredient.getId()).isNotNull();

        //when
        JobParameters jobParameters = getUniqueParameterBuilder()
                .addString("name", name)
                .toJobParameters();

        JobExecution jobExecution = launchJob(jobParameters);

        //then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        Notification notification = notificationPersistenceRepository.findAll().stream().findAny().orElse(null);

        if(notification != null) {
            log.info("enter");
            assertThat(notification.getMemberId()).isEqualTo(email);
            assertThat(notification.getMessage()).isEqualTo("회원님이 요청했던 " + name + "를 이제 냉장고에 담을 수 있습니다. (식재료 추가하기)");
            assertThat(notification.getPath()).isEqualTo("/refrigerator/addUp/info?ingredient=" + name);
            assertThat(notification.getType()).isEqualTo(NotificationType.INGREDIENT);
            assertThat(notification.getMethod()).isEqualTo(BasicHttpMethod.GET.name());
        }

        memberNotificationPersistenceRepository.findByMemberId(email).ifPresent(
                memberNotification1 -> {
                    log.info("enter");
                    assertThat(memberNotification1.getId()).isEqualTo(mn.getId());
                    assertThat(memberNotification1.getSign()).isTrue();

                });

        entityManager.getTransaction().begin();

        delete(member);

        entityManager.flush();
        entityManager.clear();

        assertThat(entityManager.find(SuggestedIngredient.class, suggestedIngredient.getId()))
                .isEqualTo(null);

        entityManager.getTransaction().commit();
    }
}