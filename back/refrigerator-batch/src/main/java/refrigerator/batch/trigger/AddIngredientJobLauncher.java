package refrigerator.batch.trigger;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.batch.Job.NotificationAddIngredientConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class AddIngredientJobLauncher {

    private final JobLauncher jobLauncher;
    private final ApplicationContext context;
    private final CurrentTime<LocalDateTime> currentTime;

    public void addIngredientJobRun(String name) {

        NotificationAddIngredientConfig ingredientConfig = context.getBean(NotificationAddIngredientConfig.class);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

        JobParameters parameters = new JobParametersBuilder()
                .addString("nowDate", currentTime.now().format(format))
                .addString("name", name)
                .toJobParameters();

        try {
            jobLauncher.run(ingredientConfig.updateIngredientJob(), parameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }
}