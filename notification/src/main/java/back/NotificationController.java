package back;

import back.config.NotificationEventConfig;
import back.dto.NoticeRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final JobLauncher jobLauncher;
    private final ApplicationContext context;

    @PostMapping("/notice/run")
    public ExitStatus noticeJobRun(@RequestBody NoticeRequestDTO request) throws Exception {
        NotificationEventConfig noticeJob = context.getBean("noticeJob", NotificationEventConfig.class);
        //return jobLauncher.run(noticeJob.noticeJob(),  request)
        return null;
    }
}
