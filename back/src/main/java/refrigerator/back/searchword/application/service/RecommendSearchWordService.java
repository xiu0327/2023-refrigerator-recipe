package refrigerator.back.searchword.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.searchword.application.domain.Ingredient;
import refrigerator.back.searchword.application.port.in.FindRecommendSearchWordUseCase;
import refrigerator.back.searchword.application.port.out.FindIngredientsByMemberPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendSearchWordService implements FindRecommendSearchWordUseCase {

    private final FindIngredientsByMemberPort findIngredientsByMemberPort;

    @Override
    public List<String> getRecommendSearchWords(String memberId) {
        return findIngredientsByMemberPort.getIngredients(memberId).stream()
                .filter(Ingredient::isExpired)
                .sorted(((i1, i2) -> i2.calculationDDay() - i1.calculationDDay()))
                .map(Ingredient::getName)
                .limit(5)
                .collect(Collectors.toList());
    }

}
