package back.config;

import back.domain.MemberNotifications;
import back.domain.Notifications;
import back.repository.NotificationRepository;
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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class NotificationAddIngredientConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final NotificationRepository notificationRepository;

    @Value("${chunkSize:1000}")
    private int chunkSize = 1000;


    @Bean
    public Job updateIngredientJob() {
        return jobBuilderFactory.get("updateIngredientJob")
                .preventRestart()
                .start(updateIngredientStep())
                .build();
    }

    @Bean
    @JobScope
    public Step updateIngredientStep() {
        return stepBuilderFactory.get("updateIngredientStep")
                .<String, Notifications>chunk(chunkSize)
                .reader(updateIngredientReader(null))
                .processor(updateIngredientProcessor(null,null))
                .writer(updateIngredientWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<String> updateIngredientReader(@Value("#{jobParameters['name']}") String name) {

        Map<String, Object> parameterValue = new HashMap<>();
        parameterValue.put("name", name);

        return new JpaPagingItemReaderBuilder<String>()
                .name("updateIngredientReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("select si.email from SuggestedIngredient si where si.name = :name group by si.email")
                .parameterValues(parameterValue)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<String, Notifications> updateIngredientProcessor(@Value("#{jobParameters['name']}") String name,
                                                                          @Value("#{jobParameters['id']}") Long id) {

        return email -> {

            MemberNotifications memberNotification = notificationRepository.findMemberNotification(email);
            memberNotification.signOn();

            return Notifications.create(
                    2,
                    "회원님이 요청했던 " + name + "를 이제 냉장고에 담을 수 있습니다. (식재료 추가하기)",
                    2,
                    "localhost:8080/api/ingredients/registered/" + id,      // back에 구현해야함.
                    "GET",
                    email);
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Notifications> updateIngredientWriter() {
        JpaItemWriter<Notifications> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
