package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.in.dto.request.RecipeIngredientVolumeDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.port.in.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.out.FindPersistenceIngredientListPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientDeductionService implements DeductionIngredientVolumeUseCase {

    private final FindPersistenceIngredientListPort findPersistenceIngredientListPort;

    @Override
    public void deduction(String memberId, List<RecipeIngredientVolumeDTO> ingredients) {
        List<Ingredient> ingredientList = isNotEmptyIngredients(memberId);
        Map<String, Double> ingredientsMap = toIngredientsMap(ingredients);
        for (Ingredient ingredient : extractNotExpired(ingredientList)) {
            ingredient.deductionVolume(ingredientsMap.get(ingredient.getName()));
        }
    }

    private List<Ingredient> extractNotExpired(List<Ingredient> ingredientList) {
        LocalDate now = LocalDate.now();
        return ingredientList.stream()
                        .filter(i -> calculationDDay(now, i.getExpirationDate()))
                        .collect(Collectors.toList());
    }

    private List<Ingredient> isNotEmptyIngredients(String memberId) {
        List<Ingredient> ingredientList = findPersistenceIngredientListPort.getIngredients(memberId);
        if (ingredientList.size() == 0){
            throw new BusinessException(IngredientExceptionType.EMPTY_INGREDIENT_LIST);
        }
        return ingredientList;
    }

    private Map<String, Double> toIngredientsMap(List<RecipeIngredientVolumeDTO> ingredients){
        Map<String, Double> result = new HashMap<>();
        for (RecipeIngredientVolumeDTO ingredient : ingredients) {
            result.put(ingredient.getName(), ingredient.getCapacity());
        }
        return result;
    }

    private boolean calculationDDay(LocalDate now, LocalDate expirationDate) {
        Period period = Period.between(now, expirationDate);
        int total = period.getDays() + period.getMonths() * 30 + period.getYears() * 365;
        return total >= 0;
    }
}
