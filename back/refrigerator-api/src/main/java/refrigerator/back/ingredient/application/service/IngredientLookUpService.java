package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.FindIngredientDetailUseCase;
import refrigerator.back.ingredient.application.port.in.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.FindRegisteredIngredientPort;
import refrigerator.back.ingredient.application.port.out.ReadIngredientPort;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientLookUpService implements FindIngredientListUseCase, FindIngredientDetailUseCase, FindRegisteredIngredientUseCase {

    private final ReadIngredientPort readIngredientPort;
    private final FindRegisteredIngredientPort findRegisteredIngredientPort;

    @Override
    public List<IngredientDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
        return readIngredientPort.getIngredientList(condition, page, size);
    }

    @Override
    public List<IngredientDTO> getIngredientListOfAll(String email) {
        return readIngredientPort.getIngredientListOfAll(email);
    }

    @Override
    public List<IngredientDTO> getIngredientListByDeadline(Long days, String email) {
        return readIngredientPort.getIngredientListByDeadline(LocalDate.now().plusDays(days), email);
    }

    @Override
    public IngredientDetailDTO getIngredient(Long id) {
        return readIngredientPort.getIngredientDetail(id);
    }

    @Override
    public List<RegisteredIngredient> getIngredientList() {
        return findRegisteredIngredientPort.findIngredientList();
    }

    @Override
    public RegisteredIngredient getIngredient(String name) {
        return findRegisteredIngredientPort.findIngredient(name);
    }
}
