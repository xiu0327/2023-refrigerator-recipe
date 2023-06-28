package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.port.in.ingredient.deducation.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

// TODO : 벌크 연산으로 바꿔야 할지도 모르겠음

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientDeductionService implements DeductionIngredientVolumeUseCase {

    private final FindIngredientListPort findIngredientListPort;

    @Override
    public void deduction(String memberId, List<IngredientDeductionDTO> ingredientDTOList) {

        List<Ingredient> ingredientList = findIngredientListPort.getIngredients(memberId);

        IngredientsEmptyCheck(ingredientList);

        Map<String, Double> ingredientDTOMap = toIngredientMap(ingredientDTOList);

        List<Ingredient> availableIngredients = extractAvailableIngredients(ingredientList, ingredientDTOMap);

        availableIngredients.forEach(ingredient -> {
            Double volume = ingredientDTOMap.get(ingredient.getName());
            if(volume != null) 
                ingredient.deductionVolume(volume);
        });
    }

    private void IngredientsEmptyCheck(List<Ingredient> ingredientList) {
        if (ingredientList.size() == 0)
            throw new BusinessException(EMPTY_INGREDIENT_LIST);
    }

    private Map<String, Double> toIngredientMap(List<IngredientDeductionDTO> ingredientDeductionDTOS){
        return ingredientDeductionDTOS.stream().collect(Collectors
                .toMap(IngredientDeductionDTO::getName, IngredientDeductionDTO::getVolume));
    }

    private List<Ingredient> extractAvailableIngredients(List<Ingredient> ingredientList, Map<String, Double> ingredientDTOMap) {

        List<Ingredient> availableIngredients = ingredientList.stream()
                .filter(i -> ChronoUnit.DAYS.between(LocalDate.now(), i.getExpirationDate()) >= 0)
                .collect(Collectors.toList());

        dtoContainCheckBySafeIngredient(ingredientDTOMap, availableIngredients.stream().map(Ingredient::getName).collect(Collectors.toList()));

        return availableIngredients;
    }

    private static void dtoContainCheckBySafeIngredient(Map<String, Double> ingredientDTOMap, List<String> availableIngredientsNameList) {
        for (String name : ingredientDTOMap.keySet()) {
            if(!availableIngredientsNameList.contains(name))
                throw new BusinessException(EXCEEDED_EXPIRATION_DATE);
        }
    }
}