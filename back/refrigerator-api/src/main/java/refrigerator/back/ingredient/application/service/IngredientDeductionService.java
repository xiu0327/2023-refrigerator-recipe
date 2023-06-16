package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
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
    public void deduction(String memberId, List<IngredientDeductionDTO> ingredients) {
        List<Ingredient> ingredientList = isNotEmptyIngredients(memberId);
        Map<String, Double> ingredientsMap = toIngredientsMap(ingredients);
        for (Ingredient ingredient : extractNotExpired(ingredientList, ingredientsMap)) {
            Double volume = ingredientsMap.get(ingredient.getName());
            if(volume != null) {
                ingredient.deductionVolume(volume);
            }
        }
    }

    private List<Ingredient> isNotEmptyIngredients(String memberId) {
        List<Ingredient> ingredientList = findPersistenceIngredientListPort.getIngredients(memberId);
        if (ingredientList.size() == 0){
            throw new BusinessException(IngredientExceptionType.EMPTY_INGREDIENT_LIST);
        }
        return ingredientList;
    }

    private Map<String, Double> toIngredientsMap(List<IngredientDeductionDTO> ingredients){
        Map<String, Double> result = new HashMap<>();
        for (IngredientDeductionDTO ingredient : ingredients) {
            result.put(ingredient.getName(), ingredient.getVolume());
        }
        return result;
    }

    private List<Ingredient> extractNotExpired(List<Ingredient> ingredientList, Map<String, Double> ingredientsMap) {
        LocalDate now = LocalDate.now();

        List<Ingredient> ingredients = ingredientList.stream()
                .filter(i -> calculationDDay(now, i.getExpirationDate()))
                .collect(Collectors.toList());

        List<String> ingredientsName = ingredients.stream().map(Ingredient::getName)
                .collect(Collectors.toList());

        for (String key : ingredientsMap.keySet()) {
            if(!ingredientsName.contains(key))
                throw new BusinessException(IngredientExceptionType.EXCEEDED_EXPIRATION_DATE);
        }

        return ingredients;
    }

    private boolean calculationDDay(LocalDate now, LocalDate expirationDate) {
        Period period = Period.between(now, expirationDate);
        int total = period.getDays() + period.getMonths() * 30 + period.getYears() * 365;
        return total >= 0;
    }
}
