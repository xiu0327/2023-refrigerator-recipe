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
import refrigerator.back.myscore.application.port.in.*;
import refrigerator.back.myscore.application.port.out.MyScoreReadPort;
import refrigerator.back.myscore.application.port.out.MyScoreWritePort;
import refrigerator.back.myscore.exception.MyRecipeScoreExceptionType;
import refrigerator.back.recipe.application.port.out.AddRecipeScorePort;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyScoreService implements FindMyScoreListUseCase, FindMyScorePreviewUseCase,
        CreateMyScoreUseCase, ModifyMyScoreUseCase {

    private final MyScoreReadPort myScoreReadPort;
    private final MyScoreWritePort myScoreWritePort;
    private final AddRecipeScorePort addRecipeScorePort;

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

    @Override
    @Transactional
    public InCookingResponseDTO cooking(String memberID, Long recipeID, Double newScore) {
        MyScore.checkScoreScope(newScore);
        Optional<MyScore> result = myScoreReadPort.findByMemberIdAndRecipeId(memberID, recipeID);
        if (result.isPresent()){ // 재요리
            return reCooking(newScore, result.get());
        }
        return firstCooking(memberID, recipeID, newScore);
    }

    private InCookingResponseDTO firstCooking(String memberID, Long recipeID, Double score) {
        Long scoreID = myScoreWritePort.save(MyScore.create(memberID, recipeID, score));
        addRecipeScorePort.addScore(recipeID, score, 1);
        return InCookingResponseDTO.builder()
                .scoreID(scoreID)
                .isCreated(true).build();
    }

    private InCookingResponseDTO reCooking(Double newScore, MyScore myScore) {
        Double oldScore = myScore.getScore();
        myScore.modify(newScore);
        addRecipeScorePort.addScore(myScore.getRecipeID(), newScore - oldScore, 0);
        return InCookingResponseDTO.builder()
                .scoreID(myScore.getScoreID())
                .isCreated(false).build();
    }


}
