package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.MyIngredientCollection;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.MyIngredientDto;
import refrigerator.back.ingredient.application.port.in.ingredient.deducation.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindMyIngredientMapPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.UpdateIngredientVolumePort;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientDeductionService implements DeductionIngredientVolumeUseCase {

    private final FindMyIngredientMapPort findMyIngredientMapPort;
    private final UpdateIngredientVolumePort updateIngredientVolumePort;
    private final CurrentTime<LocalDate> currentTime;

    @Override
    public void deduction(String memberId, List<IngredientDeductionDTO> recipeIngredients) {
        MyIngredientCollection myIngredients = findMyIngredientMapPort.getMap(memberId, currentTime.now());
        myIngredients.deductByRecipeIngredient(recipeIngredients);
        myIngredients.updateMyIngredientVolume(updateIngredientVolumePort);
    }
}
