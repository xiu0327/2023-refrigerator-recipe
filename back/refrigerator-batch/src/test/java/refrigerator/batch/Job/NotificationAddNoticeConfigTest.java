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
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.notice.application.domain.Notice;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.outbound.repository.jpa.NotificationPersistenceRepository;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "job.name=noticeBatch_Job")
@Slf4j
class NotificationAddNoticeConfigTest extends BatchTestSupport{

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
    @DisplayName("공지사항 알림 생성 테스트")
    void addNotificationByNotice() throws Exception {

        // given
        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        String email = "email123@gmail.com";

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

        Notice notice = Notice.builder()
                .title("ㅎㅇㅎㅇ")
                .content("ㅎㅇㅎㅇ!!")
                .build();

        save(notice);

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(email)
                .sign(false)
                .build();

        MemberNotification mn = memberNotificationPersistenceRepository.save(memberNotification);

        entityManager.getTransaction().commit();

        assertThat(notice.getId()).isNotNull();
        assertThat(member.getId()).isNotNull();

        //when
        JobParameters jobParameters = getUniqueParameterBuilder()
                .addString("title", notice.getTitle())
                .addLong("id", notice.getId())
                .toJobParameters();

        JobExecution jobExecution = launchJob(jobParameters);

        //then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        Notification notification = notificationPersistenceRepository.findAll().stream().findAny().orElse(null);

        if(notification != null) {
            log.info("enter");
            assertThat(notification.getMemberId()).isEqualTo(email);
            assertThat(notification.getMessage()).isEqualTo("공지사항이 추가되었어요! '" + notice.getTitle() + "'");
            assertThat(notification.getPath()).isEqualTo("/api/notice/" + notice.getId());

        }

        memberNotificationPersistenceRepository.findByMemberId(email).ifPresent(
                memberNotification1 -> {
                    log.info("enter");
                    assertThat(memberNotification1.getId()).isEqualTo(mn.getId());
                    assertThat(memberNotification1.getSign()).isTrue();

                });

        // after
        entityManager.getTransaction().begin();

        delete(member);
        delete(notice);

        entityManager.flush();
        entityManager.clear();

        entityManager.getTransaction().commit();
    }
}