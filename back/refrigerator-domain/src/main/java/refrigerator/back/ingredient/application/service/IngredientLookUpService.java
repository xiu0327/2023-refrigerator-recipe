package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.port.in.ingredient.lookUp.FindIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.ingredient.lookUp.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientLookUpService implements FindIngredientListUseCase, FindIngredientUseCase {

    private final FindIngredientListPort findIngredientListPort;
    private final FindIngredientPort findIngredientPort;
    
    @Override
    public List<IngredientDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
        return findIngredientListPort.getIngredientList(condition, page, size);
    }

    @Override
    public List<IngredientDTO> getIngredientListOfAll(String email) {
        return findIngredientListPort.getIngredientListOfAll(email);
    }

    @Override
    public List<IngredientDTO> getIngredientListByDeadline(Long days, String email) {
        return findIngredientListPort.getIngredientListByDeadline(LocalDate.now().plusDays(days), email);
    }

    @Override
    public IngredientDetailDTO getIngredient(Long id) {
        return findIngredientPort.getIngredientDetail(id);
    }

}