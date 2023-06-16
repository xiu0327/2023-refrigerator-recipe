package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.api.BasicListRequestDTO;
import refrigerator.back.global.common.api.MemberInformation;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.ingredient.adapter.in.dto.IngredientDeductionRequestDTO;
import refrigerator.back.ingredient.adapter.in.mapper.InIngredientMapper;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.global.exception.api.ValidationExceptionHandler.*;
import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@RestController
@RequiredArgsConstructor
public class IngredientDeductionController {

    private final DeductionIngredientVolumeUseCase deductionIngredientVolumeUseCase;
    private final FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;
    private final InIngredientMapper mapper;

    @PutMapping("/api/ingredients/deduction")
    public void deductionIngredientVolume(@RequestBody @Valid BasicListRequestDTO<IngredientDeductionRequestDTO> request, BindingResult bindingResult){
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        List<RegisteredIngredient> ingredientList = findRegisteredIngredientUseCase.getIngredientList();

        // 데이터 검증

        for (IngredientDeductionRequestDTO dto : request.getData()) {
             ingredientList.stream()
                    .filter(i -> i.getName().equals(dto.getName()) && i.getUnit().equals(dto.getUnit()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(NOT_VALID_REQUEST_BODY));
        }

        deductionIngredientVolumeUseCase.deduction(
                MemberInformation.getMemberEmail(),
                request.getData().stream().map(mapper::toIngredientDeductionDTO).collect(Collectors.toList()));
    }
}
