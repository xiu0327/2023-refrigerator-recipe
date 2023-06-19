package refrigerator.back.recipe_searchword.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.recipe_searchword.application.port.in.FindRecommendSearchWordUseCase;
import refrigerator.back.recipe_searchword.application.port.out.FindIngredientsByMemberPort;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendSearchWordService implements FindRecommendSearchWordUseCase {

    private final FindIngredientsByMemberPort findIngredientsByMemberPort;

    @Override
    public List<String> getRecommendSearchWords(String memberId) {
        List<String> recommendWords = findIngredientsByMemberPort.getIngredients(memberId);
        if (recommendWords.size() <= 0){
            return new ArrayList<>();
        }
        return recommendWords;
    }

}
