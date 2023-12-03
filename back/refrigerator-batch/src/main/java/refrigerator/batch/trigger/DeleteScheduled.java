package refrigerator.batch.trigger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DeleteScheduled {

    @Autowired
    private Job periodicDeleteScheduleJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 0 1,15 * *")
    public void deleteScheduled() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> jobParameterMap = new HashMap<>();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        jobParameterMap.put("createDate", new JobParameter(LocalDateTime.now().format(format)));

        JobParameters parameters = new JobParameters(jobParameterMap);

        JobExecution jobExecution = jobLauncher.run(periodicDeleteScheduleJob, parameters);
    }
}
