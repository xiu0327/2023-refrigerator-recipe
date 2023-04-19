package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.adapter.in.dto.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisteredResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientResponseDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.port.in.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.FindIngredientDetailUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredientPort;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientLookUpService implements FindIngredientListUseCase, FindIngredientDetailUseCase {

    private final ReadIngredientPort readIngredientPort;

    @Override
    public List<IngredientResponseDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
        return readIngredientPort.getIngredientList(condition, page, size);
    }

    @Override
    public List<IngredientResponseDTO> getIngredientListOfAll(String email) {
        return readIngredientPort.getIngredientListOfAll(email);
    }

    @Override
    public List<IngredientRegisteredResponseDTO> getIngredientListOfRegistered() {
        return readIngredientPort.getIngredientListOfRegistered();
    }

    @Override
    public List<IngredientResponseDTO> getIngredientListByDeadline(Long days, String email) {
        return readIngredientPort.getIngredientListByDeadline(LocalDate.now().plusDays(days), email);
    }

    @Override
    public IngredientDetailResponseDTO getIngredient(Long id) {
        return readIngredientPort.getIngredientDetail(id);
    }
}
