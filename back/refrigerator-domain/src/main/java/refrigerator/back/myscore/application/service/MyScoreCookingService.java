package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.myscore.application.port.in.CookingUseCase;
import refrigerator.back.myscore.application.port.out.FindMyScorePort;
import refrigerator.back.myscore.application.port.out.SaveMyScorePort;
import refrigerator.back.recipe.application.port.in.RecipeScoreModifyHandler;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MyScoreCookingService implements CookingUseCase {

    private final SaveMyScorePort saveMyScorePort;
    private final FindMyScorePort findMyScorePort;
    private final RecipeScoreModifyHandler handler;
    private final CurrentTime<LocalDateTime> currentTime;

    @Override
    public Long firstCooking(String memberId, Long recipeId, Double score) {
        MyScore myScore = MyScore.create(memberId, recipeId, score, currentTime.now(), handler);
        return saveMyScorePort.save(myScore);
    }

    @Override
    public void retryCooking(Long scoreId, Double score) {
        MyScore myScore = findMyScorePort.findById(scoreId);
        myScore.update(score, handler);
    }

}
