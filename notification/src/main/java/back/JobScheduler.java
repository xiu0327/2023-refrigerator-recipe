package back;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job scheduleJob;

    @Scheduled(cron = "1 * * * * *")
    public void jobScheduled() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
                                JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> jobParameterMap = new HashMap<>();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date time = new Date();

        String time1 = format1.format(time);

        jobParameterMap.put("date", new JobParameter(time1));

        JobParameters parameters = new JobParameters(jobParameterMap);

        JobExecution jobExecution = jobLauncher.run(scheduleJob, parameters);
    }

}
