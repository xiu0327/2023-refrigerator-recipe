package back.config;

import back.domain.Notifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class NotificationAddNoticeConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Value("${chunkSize:1000}")
    private int chunkSize = 1000;

    @Bean
    public Job noticeJob(){
        return jobBuilderFactory.get("noticeJob")
                .preventRestart()
                .start(noticeStep())
                .build();
    }

    @Bean
    @JobScope
    public Step noticeStep() {
        return stepBuilderFactory.get("noticeStep")
                .<String, Notifications>chunk(chunkSize)
                .reader(noticeNotificationReader())
                .processor(noticeNotificationProcessor(null, null))
                .writer(noticeNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<String> noticeNotificationReader() {
        return new JpaPagingItemReaderBuilder<String>()
                .name("noticeNotificationReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("select m.email from Member m")
                .build();

    }

    @Bean
    @StepScope
    public ItemProcessor<String, Notifications> noticeNotificationProcessor(
                                                                    @Value("#{jobParameters['title']}") String title,
                                                                    @Value("#{jobParameters['id']}") Long id ) {


        return email -> Notifications.create(
                3,
                createMessage(title),
                3,
                "localhost:8080/api/notice/" + id,
                "GET",
                email
        );
    }

    @Bean
    @StepScope
    public JpaItemWriter<Notifications> noticeNotificationWriter() {
        JpaItemWriter<Notifications> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    private String createMessage(String title) {
        return "공지사항이 추가되었어요! '" + title + "'";
    }
}
