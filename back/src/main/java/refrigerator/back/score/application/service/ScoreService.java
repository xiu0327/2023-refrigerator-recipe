package refrigerator.back.score.application.service;

import org.springframework.stereotype.Service;
import refrigerator.back.score.application.port.in.FindMyRecipeScoreUseCase;

@Service
public class ScoreService implements FindMyRecipeScoreUseCase {
    @Override
    public double getMyScore(Long recipeID) {
        return 0;
    }
}
