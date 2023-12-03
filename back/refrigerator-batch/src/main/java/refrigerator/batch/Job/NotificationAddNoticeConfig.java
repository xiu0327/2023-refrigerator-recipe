package refrigerator.batch.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;
import refrigerator.batch.common.querydsl.expression.Expression;
import refrigerator.batch.common.querydsl.options.QuerydslNoOffsetNumberOptions;
import refrigerator.batch.common.querydsl.reader.QuerydslNoOffsetPagingItemReader;

import javax.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

import static refrigerator.back.member.application.domain.QMember.member;


@RequiredArgsConstructor
@Configuration
@Slf4j
@ConditionalOnProperty(name = "job.name", havingValue = "noticeBatch_Job")
public class NotificationAddNoticeConfig {

    public static final String BATCH_NAME = "noticeBatch";
    public static final String JOB_NAME = BATCH_NAME + "_Job";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final ModifyMemberNotificationPort modifyMemberNotificationPort;

    private final CurrentTime<LocalDateTime> currentTime;

    @Value("${chunkSize:1000}")
    private int chunkSize = 1000;

    @Bean(name = JOB_NAME)
    public Job noticeJob(){
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .start(noticeStep())
                .build();
    }

    @Bean
    @JobScope
    public Step noticeStep() {
        return stepBuilderFactory.get("noticeStep")
                .<Member, Notification>chunk(chunkSize)
                .reader(noticeNotificationReader())
                .processor(noticeNotificationProcessor(null, null))
                .writer(noticeNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslNoOffsetPagingItemReader<Member> noticeNotificationReader() {

        QuerydslNoOffsetNumberOptions<Member, Long> option = new QuerydslNoOffsetNumberOptions<>(member.id, Expression.ASC);

        return new QuerydslNoOffsetPagingItemReader<>(entityManagerFactory, chunkSize, option,
                queryFactory -> queryFactory
                        .selectFrom(member)
                        .where(member.memberStatus
                                .eq(MemberStatus.STEADY_STATUS)));
    }

    @Bean
    @StepScope
    public ItemProcessor<Member, Notification> noticeNotificationProcessor(
            @Value("#{jobParameters['title']}") String title,
            @Value("#{jobParameters['id']}") Long id ) {

        return member -> {

            modifyMemberNotificationPort.modify(member.getEmail(), true);


            Notification notification = Notification.create(
                    NotificationType.NOTICE,
                    "/api/notice/" + id,
                    member.getEmail(),
                    BasicHttpMethod.GET.name(),
                    currentTime.now());
            notification.createNoticeMessage(title);
            return notification;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Notification> noticeNotificationWriter() {
        JpaItemWriter<Notification> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
