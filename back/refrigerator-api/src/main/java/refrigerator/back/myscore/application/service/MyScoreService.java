package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.myscore.adapter.in.dto.response.InCookingResponseDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreListDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScorePreviewDTO;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.myscore.application.port.in.CreateMyScoreUseCase;
import refrigerator.back.myscore.application.port.in.FindMyScoreListUseCase;
import refrigerator.back.myscore.application.port.in.FindMyScorePreviewUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyScoreUseCase;
import refrigerator.back.myscore.application.port.out.MyScoreReadPort;
import refrigerator.back.myscore.application.port.out.MyScoreWritePort;
import refrigerator.back.myscore.exception.MyRecipeScoreExceptionType;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;
import refrigerator.back.recipe.application.port.out.AddRecipeScorePort;
import refrigerator.back.recipe.application.port.out.GetRecipeScoreDataPort;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyScoreService implements FindMyScoreListUseCase, FindMyScorePreviewUseCase,
        CreateMyScoreUseCase, ModifyMyScoreUseCase {

    private final MyScoreReadPort myScoreReadPort;
    private final MyScoreWritePort myScoreWritePort;
    private final AddRecipeScorePort addRecipeScorePort;
    private final GetRecipeScoreDataPort getRecipeScoreDataPort;

    @Override
    @Transactional(readOnly = true)
    public InMyScoreListDTO<InMyScoreDTO> findMyScoreList(String memberID, int page, int size) {
        return new InMyScoreListDTO<>(myScoreReadPort.getMyScoreList(memberID, page, size));
    }

    @Override
    public InMyScoreListDTO<InMyScorePreviewDTO> findPreviewList(String memberID, int size) {
        return myScoreReadPort.getMyScorePreview(memberID, size);
    }

    @Override
    @Transactional
    public void modify(Long scoreID, double newScore) {
        MyScore.checkScoreScope(newScore);
        MyScore myScore = myScoreReadPort.findById(scoreID)
                .orElseThrow(() -> new BusinessException(MyRecipeScoreExceptionType.NOT_FOUND_SCORE));
        Double oldScore = myScore.getScore();
        myScore.modify(newScore);
        addRecipeScorePort.addScore(myScore.getRecipeID(), newScore - oldScore, 0);
    }

    // TODO : MyScore 변경 로직과 RecipeScore 변경 로직 리팩터링 후 비동기처리
    @Override
    @Transactional
    public InCookingResponseDTO cooking(String memberID, Long recipeID, Double newScore) {
        MyScore.checkScoreScope(newScore);
        Optional<MyScore> result = myScoreReadPort.findByMemberIdAndRecipeId(memberID, recipeID);
        if (result.isPresent()){ // 재요리
            return reCooking(recipeID, newScore, result.get());
        }
        return firstCooking(memberID, recipeID, newScore);
    }

    private InCookingResponseDTO firstCooking(String memberID, Long recipeID, Double score) {
        Long scoreID = myScoreWritePort.save(MyScore.create(memberID, recipeID, score));
//        addRecipeScorePort.addScore(recipeID, score, 1);
        RecipeScore recipeScore = getRecipeScoreDataPort.findOne(recipeID);
        recipeScore.toCalculateTotalScore(score);
        return InCookingResponseDTO.builder()
                .scoreID(scoreID)
                .isCreated(true).build();
    }

    private InCookingResponseDTO reCooking(Long recipeId, Double newScore, MyScore myScore) {
        Double oldScore = myScore.getScore();
        myScore.modify(newScore);
//        addRecipeScorePort.addScore(myScore.getRecipeID(), newScore - oldScore, 0);
        RecipeScore recipeScore = getRecipeScoreDataPort.findOne(recipeId);
        recipeScore.toRecalculateTotalScore(oldScore, newScore);
        return InCookingResponseDTO.builder()
                .scoreID(myScore.getScoreID())
                .isCreated(false).build();
    }


}
