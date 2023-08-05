package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.MyIngredientCollection;
import refrigerator.back.ingredient.application.dto.MyIngredientDto;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindMyIngredientMapPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.UpdateIngredientVolumePort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;
import refrigerator.back.ingredient.outbound.dto.OutMyIngredientDto;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapper;
import refrigerator.back.ingredient.outbound.repository.IngredientLookUpQueryRepository;
import refrigerator.back.ingredient.outbound.repository.IngredientUpdateQueryRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class IngredientDeductionAdapter implements FindMyIngredientMapPort, UpdateIngredientVolumePort {

    private final IngredientLookUpQueryRepository selectQueryRepository;
    private final IngredientUpdateQueryRepository updateQueryRepository;
    private final OutIngredientMapper mapper;

    @Override
    public MyIngredientCollection getMap(String memberId, LocalDate now) {
        Map<String, MyIngredientDto> result = new HashMap<>();
        List<OutMyIngredientDto> myIngredients = selectQueryRepository.selectMyIngredients(memberId, now);
        // TODO : 식재료 중복 처리 필요
        myIngredients.forEach(ingredient -> result.put(ingredient.getName(), ingredient.mapping(mapper)));
        return new MyIngredientCollection(result);
    }

    @Override
    public void updateToVolume(Long id, Double volume) {
        updateQueryRepository.updateIngredientVolume(id, volume)
                .throwExceptionWhenNotAllowDuplicationResource(IngredientExceptionType.NOT_FOUND_INGREDIENT);
    }
}
