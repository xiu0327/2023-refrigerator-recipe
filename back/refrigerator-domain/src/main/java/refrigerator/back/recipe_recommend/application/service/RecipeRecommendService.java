package refrigerator.back.recipe_recommend.application.service;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.Current;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.recipe_recommend.application.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.application.domain.RecommendMatchPercent;
import refrigerator.back.recipe_recommend.application.domain.RecipeRecommendDomainService;
import refrigerator.back.recipe_recommend.application.port.in.RecommendRecipeUseCase;
import refrigerator.back.recipe_recommend.application.port.out.GetMyIngredientNameDataPort;
import refrigerator.back.recipe_recommend.application.port.out.GetRecipeRecommendInfoDataPort;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;

@Service
@RequiredArgsConstructor
public class RecipeRecommendService implements RecommendRecipeUseCase {

    private final GetMyIngredientNameDataPort findMyIngredientNames;
    private final GetRecipeRecommendInfoDataPort findRecipeRecommendInfo;
    private final CurrentTime<LocalDate> currentTime;

    @Override
    @Transactional(readOnly = true)
    public List<InRecipeRecommendDTO> recommend(String memberId) {
        RecipeRecommendDomainService recommend = getRecipeRecommend(memberId);
        List<Long> ids = recommend.extractTopTenMatchPercentIds();
        return findRecipeRecommendInfo.findInfoByIds(ids).stream()
                .map(dto -> dto.calculateMatchRate(recommend.findMatchPercentByRecipeId(dto.getRecipeID())))
                .sorted(comparingDouble(InRecipeRecommendDTO::getMatch).reversed())
                .collect(Collectors.toList());
    }

    private RecipeRecommendDomainService getRecipeRecommend(String memberId) {
        Map<Long, RecommendMatchPercent> result = new HashMap<>();
        findRecipeRecommendInfo.findRecipeIngredientNames().forEach((key, value) ->
                result.put(key, new RecommendMatchPercent(value))
        );
        return new RecipeRecommendDomainService(result, findMyIngredientNames.findMyIngredientNames(currentTime.now(), memberId));
    }
}
