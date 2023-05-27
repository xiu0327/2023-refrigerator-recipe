package refrigerator.back.searchword.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.searchword.application.domain.Ingredient;
import refrigerator.back.searchword.application.port.in.FindRecommendSearchWordUseCase;
import refrigerator.back.searchword.application.port.out.FindIngredientsByMemberPort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
