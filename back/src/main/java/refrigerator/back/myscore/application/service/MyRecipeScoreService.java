package refrigerator.back.myscore.application.service;

import org.springframework.stereotype.Service;
import refrigerator.back.myscore.application.port.in.AssessMyRecipeScoreUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScoreUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyRecipeScoreUseCase;

@Service
public class MyRecipeScoreService implements AssessMyRecipeScoreUseCase, FindMyRecipeScoreUseCase, ModifyMyRecipeScoreUseCase {

    @Override
    public Long assessRecipeScore(String memberID, Long recipeID, double score) {
        return null;
    }

    @Override
    public double getMyScore(String memberID, Long recipeID) {
        return 0;
    }

    @Override
    public void modifyMyRecipeScore(Long scoreID, double newScore) {

    }
}
