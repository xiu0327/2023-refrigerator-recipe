package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.myscore.adapter.in.dto.CookingResponseDTO;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;
import refrigerator.back.myscore.application.domain.MyRecipeScoreListDomain;
import refrigerator.back.myscore.application.port.in.*;
import refrigerator.back.myscore.application.port.out.MyRecipeScoreReadPort;
import refrigerator.back.myscore.application.port.out.MyRecipeScoreWritePort;
import refrigerator.back.recipe.application.port.out.AddRecipeScorePort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyRecipeScoreService implements FindMyRecipeScoreListUseCase, FindMyRecipeScorePreviewUseCase,
        CookingUseCase, ModifyMyRecipeScoreUseCase {

    private final MyRecipeScoreReadPort myRecipeScoreReadPort;
    private final MyRecipeScoreWritePort myRecipeScoreWritePort;
    private final AddRecipeScorePort addRecipeScorePort;

    @Override
    @Transactional(readOnly = true)
    public MyRecipeScoreListDomain findMyScoreList(String memberID, int page, int size) {
        return MyRecipeScoreListDomain.builder()
                .scores(myRecipeScoreReadPort.getMyRecipeScoreList(memberID, page, size))
                .build();
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

    @Override
    @Transactional
    public void modifyMyRecipeScore(Long scoreID, double newScore) {
        MyRecipeScoreDomain myScore = myRecipeScoreReadPort.getPersistenceMyRecipeById(scoreID);
        modify(newScore, myScore);
    }

    @Override
    @Transactional
    public CookingResponseDTO cooking(String memberID, Long recipeID, Double newScore) {
        try{ // 재요리
            MyRecipeScoreDomain myScore = myRecipeScoreReadPort.getMyRecipeScore(memberID, recipeID);
            Long scoreID = modify(newScore, myScore);
            return CookingResponseDTO.builder()
                    .scoreID(scoreID)
                    .isCreated(false).build();
        }catch (RuntimeException e){ // 최초 요리
            Long scoreID = create(memberID, recipeID, newScore);
            return CookingResponseDTO.builder()
                    .scoreID(scoreID)
                    .isCreated(true).build();
        }
    }

    private Long modify(double newScore, MyRecipeScoreDomain myScore) {
        Double oldScore = myScore.getScore();
        myScore.modify(newScore);
        Long scoreID = myRecipeScoreWritePort.save(myScore);
        addRecipeScorePort.addScore(myScore.getRecipeID(), newScore - oldScore, 0);
        return scoreID;
    }

    private Long create(String memberID, Long recipeID, double score) {
        MyRecipeScoreDomain domain = MyRecipeScoreDomain.builder()
                .memberID(memberID)
                .recipeID(recipeID)
                .score(score).build();
        Long scoreID = myRecipeScoreWritePort.save(domain);
        addRecipeScorePort.addScore(recipeID, score, 1);
        return scoreID;
    }

}
