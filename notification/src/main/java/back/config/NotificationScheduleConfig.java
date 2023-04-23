package back.config;

import back.repository.NotificationRepository;
import back.domain.Notifications;
import back.dto.OutIngredientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;

import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// 자정마다 해야할 것

@RequiredArgsConstructor
@Configuration
@Slf4j
public class NotificationScheduleConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final NotificationRepository notificationRepository;

    @Value("${chunkSize:1000}")
    private int chunkSize = 1000;

    @Bean
    public Job scheduleJob(){
        return jobBuilderFactory.get("scheduleJob")
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(deleteNotificationStep())
                .next(createDeadlineNotificationByOneStep())
                .next(createDeadlineNotificationByThreeStep())
                .build();
    }

    // 알림 삭제 Step

    @Bean
    @JobScope
    public Step deleteNotificationStep() {

        return stepBuilderFactory.get("deleteNotificationStep")
                .tasklet((contribution, chunkContext) -> {

                    notificationRepository.deleteNotification(LocalDateTime.now());
                    notificationRepository.deleteDeadlineNotification(LocalDateTime.now().minusDays(14));
                    
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // 임박 식재료 알림 생성 Step (1일)

    @Bean
    @JobScope
    public Step createDeadlineNotificationByOneStep() {
        return stepBuilderFactory.get("createDeadlineNotificationStep")
                .<OutIngredientDTO, Notifications> chunk(chunkSize)
                .reader(createDeadlineNotificationByOneReader())
                .processor(createDeadlineNotificationByOneProcessor())
                .writer(createDeadlineNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<OutIngredientDTO> createDeadlineNotificationByOneReader() {

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("date", LocalDate.now().plusDays(1));

        return new JpaPagingItemReaderBuilder<OutIngredientDTO>()
                .name("createDeadlineNotificationReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("select new back.dto.OutIngredientDTO(i.email, min(i.name) as name, count(i.id) as ingredient_count) from Ingredient i where i.expirationDate = :date group by i.email")
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<OutIngredientDTO, Notifications> createDeadlineNotificationByOneProcessor() {

        return dto -> Notifications.create(
                "image1.png",
                createNotificationMessage(dto.getName(), dto.getCount(), 1L),
                "1",
                "localhost:8080/api/ingredients/deadline/1",
                "GET",
                dto.getEmail()
        );
    }

    // 임박 식재료 알림 생성 Step (3일)

    @Bean
    @JobScope
    public Step createDeadlineNotificationByThreeStep() {
        return stepBuilderFactory.get("createDeadlineNotificationStep")
                .<OutIngredientDTO, Notifications> chunk(chunkSize)
                .reader(createDeadlineNotificationByThreeReader())
                .processor(createDeadlineNotificationByThreeProcessor())
                .writer(createDeadlineNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<OutIngredientDTO> createDeadlineNotificationByThreeReader() {

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("date", LocalDate.now().plusDays(3));

        return new JpaPagingItemReaderBuilder<OutIngredientDTO>()
                .name("createDeadlineNotificationReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("select new back.dto.OutIngredientDTO(i.email, min(i.name) as name, count(i.id) as ingredient_count) from Ingredient i where i.expirationDate = :date group by i.email")
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<OutIngredientDTO, Notifications> createDeadlineNotificationByThreeProcessor() {

        return dto -> Notifications.create(
                "image1.png",
                createNotificationMessage(dto.getName(), dto.getCount(), 3L),
                "1",
                "localhost:8080/api/ingredients/deadline/3",
                "GET",
                dto.getEmail()
        );
    }

    @Bean
    @StepScope
    public JpaItemWriter<Notifications> createDeadlineNotificationWriter() {
        JpaItemWriter<Notifications> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    private String createNotificationMessage(String name, Long count, Long days) {
        return name + " 외 "+ count + "개 식재료의 소비기한이 " + days + "일 남았습니다. 식재료 확인하러가기!";
    }
}
