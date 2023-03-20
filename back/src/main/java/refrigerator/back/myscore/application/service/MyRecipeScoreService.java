package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.myscore.adapter.in.dto.MyRecipeScoreListResponseDTO;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;
import refrigerator.back.myscore.application.domain.MyRecipeScoreListDomain;
import refrigerator.back.myscore.application.port.in.AssessMyRecipeScoreUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScoreListUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScorePreviewUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyRecipeScoreUseCase;
import refrigerator.back.myscore.application.port.out.MyRecipeScoreReadPort;
import refrigerator.back.myscore.application.port.out.MyRecipeScoreWritePort;
import refrigerator.back.recipe.application.port.out.AddRecipeScorePort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyRecipeScoreService implements AssessMyRecipeScoreUseCase, FindMyRecipeScoreListUseCase,
        ModifyMyRecipeScoreUseCase, FindMyRecipeScorePreviewUseCase {

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
    @Transactional(readOnly = true)
    public MyRecipeScoreListDomain findMyScoreList(String memberID, int page, int size) {
        return MyRecipeScoreListDomain.builder()
                .scores(myRecipeScoreReadPort.getMyRecipeScoreList(memberID, page, size))
                .build();
    }

    @Override
    @Transactional
    public void modifyMyRecipeScore(Long scoreID, double newScore) {
        MyRecipeScoreDomain myScore = myRecipeScoreReadPort.getPersistenceMyRecipeById(scoreID);
        Double oldScore = myScore.getScore();
        myScore.modify(newScore);
        myRecipeScoreWritePort.save(myScore);
        addRecipeScorePort.addScore(myScore.getRecipeID(), newScore - oldScore, 0);
    }

    @Override
    public MyRecipeScoreListDomain findPreview(String memberID, int size) {
        List<MyRecipeScoreDomain> scores = myRecipeScoreReadPort.getMyRecipeScorePreview(memberID);
        return MyRecipeScoreListDomain.builder()
                .scores(scores.stream()
                        .limit(size).collect(Collectors.toList()))
                .count(scores.size())
                .build();
    }
}
