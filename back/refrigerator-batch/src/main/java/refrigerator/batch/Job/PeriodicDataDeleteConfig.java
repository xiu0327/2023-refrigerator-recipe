package refrigerator.batch.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refrigerator.back.comment.adapter.repository.query.CommentBatchQueryRepository;
import refrigerator.back.comment.adapter.repository.dao.CommentRepository;
import refrigerator.back.ingredient.adapter.repository.IngredientPersistenceRepository;
import refrigerator.back.mybookmark.adapter.out.repository.BookmarkRepository;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class PeriodicDataDeleteConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CommentRepository commentRepository;
    private final CommentBatchQueryRepository commentBatchQueryRepository;
    private final IngredientPersistenceRepository ingredientPersistenceRepository;
    private final BookmarkRepository bookmarkRepository;

    @Bean
    public Job periodicDeleteScheduleJob() {
        return jobBuilderFactory.get("periodicDeleteScheduleJob")
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
                    commentRepository.delete();
//                    commentRepository.deleteCommentHeart();
//                    commentRepository.delete();

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step deleteIngredientStep() {
        return stepBuilderFactory.get("deleteIngredientStep")
                .tasklet((contribution, chunkContext) -> {
                    ingredientPersistenceRepository.deleteIngredient();

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step deleteBookmarkStep() {
        return stepBuilderFactory.get("deleteBookmarkStep")
                .tasklet((contribution, chunkContext) -> {
                    bookmarkRepository.deleteMyBookmark();

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
