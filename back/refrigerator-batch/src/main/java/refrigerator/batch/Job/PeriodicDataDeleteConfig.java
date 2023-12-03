package refrigerator.batch.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refrigerator.back.comment.application.port.batch.DeleteCommentUseCase;
import refrigerator.back.ingredient.application.port.batch.DeleteIngredientBatchPort;
import refrigerator.back.mybookmark.application.port.batch.DeleteBookmarkBatchPort;

@RequiredArgsConstructor
@Configuration
@Slf4j
@ConditionalOnProperty(name = "job.name", havingValue = "periodicDeleteScheduleBatch_Job")
public class PeriodicDataDeleteConfig {

    public static final String BATCH_NAME = "periodicDeleteScheduleBatch";
    public static final String JOB_NAME = BATCH_NAME + "_Job";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DeleteIngredientBatchPort deleteIngredientBatchPort;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final DeleteBookmarkBatchPort deleteBookmarkBatchPort;

    @Bean(name = JOB_NAME)
    public Job periodicDeleteScheduleJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .start(deleteCommentStep())
                .next(deleteIngredientStep())
                .next(deleteBookmarkStep())
                .build();
    }

    @Bean
    @JobScope
    public Step deleteCommentStep() {
        return stepBuilderFactory.get("deleteCommentStep")
                .tasklet((contribution, chunkContext) -> {
                    deleteCommentUseCase.deleteComment();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step deleteIngredientStep() {
        return stepBuilderFactory.get("deleteIngredientStep")
                .tasklet((contribution, chunkContext) -> {
                    deleteIngredientBatchPort.deleteIngredients();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step deleteBookmarkStep() {
        return stepBuilderFactory.get("deleteBookmarkStep")
                .tasklet((contribution, chunkContext) -> {
                    deleteBookmarkBatchPort.deleteMyBookmark();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
