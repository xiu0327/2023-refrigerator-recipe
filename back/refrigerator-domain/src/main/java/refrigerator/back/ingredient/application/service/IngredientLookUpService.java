package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
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
    public List<IngredientResponseDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
        return readIngredientPort.getIngredientList(condition, page, size);
    }

    @Override
    public List<IngredientResponseDTO> getIngredientListOfAll(String email) {
        return readIngredientPort.getIngredientListOfAll(email);
    }

    @Override
    public List<IngredientResponseDTO> getIngredientListByDeadline(Long days, String email) {
        return readIngredientPort.getIngredientListByDeadline(LocalDate.now().plusDays(days), email);
    }

    @Override
    public IngredientDetailResponseDTO getIngredient(Long id) {
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
