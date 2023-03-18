package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;
import refrigerator.back.myscore.application.port.in.AssessMyRecipeScoreUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScoreListUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyRecipeScoreUseCase;
import refrigerator.back.myscore.application.port.out.MyRecipeScoreReadPort;
import refrigerator.back.myscore.application.port.out.MyRecipeScoreWritePort;
import refrigerator.back.recipe.application.port.out.AddRecipeScorePort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyRecipeScoreService implements AssessMyRecipeScoreUseCase, FindMyRecipeScoreListUseCase, ModifyMyRecipeScoreUseCase {

    private final MyRecipeScoreReadPort myRecipeScoreReadPort;
    private final MyRecipeScoreWritePort myRecipeScoreWritePort;
    private final AddRecipeScorePort addRecipeScorePort;

    @Override
    @Transactional
    public Long assessRecipeScore(String memberID, Long recipeID, double score) {
        MyRecipeScoreDomain domain = MyRecipeScoreDomain.builder()
                .memberID(memberID)
                .recipeID(recipeID)
                .score(score).build();
        Long scoreID = myRecipeScoreWritePort.save(domain);
        addRecipeScorePort.addScore(recipeID, score, 1);
        return scoreID;
    }

    @Override
    public List<MyRecipeScoreDomain> findMyScoreList(String memberID, int page, int size) {
        return myRecipeScoreReadPort.getMyRecipeScoreList(memberID, page, size);
    }

    @Override
    public void modifyMyRecipeScore(Long scoreID, double newScore) {
        MyRecipeScoreDomain myScore = myRecipeScoreReadPort.getPersistenceMyRecipeById(scoreID);
        Double oldScore = myScore.getScore();
        myScore.modify(newScore);
        myRecipeScoreWritePort.save(myScore);
        addRecipeScorePort.addScore(myScore.getRecipeID(), newScore - oldScore, 0);
    }
}
