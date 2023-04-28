package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.MemberInformation;
import refrigerator.back.ingredient.adapter.in.dto.*;
import refrigerator.back.ingredient.adapter.mapper.IngredientMapper;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.FindIngredientDetailUseCase;

import javax.validation.Valid;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class IngredientLookUpController {

    private final FindIngredientDetailUseCase findIngredientDetailUseCase;
    private final FindIngredientListUseCase findIngredientListUseCase;
    private final IngredientMapper mapper;

    @GetMapping("/api/ingredients")
    public IngredientListResponseDTO<IngredientResponseDTO> findIngredientList(@RequestBody IngredientLookUpRequestDTO requestDTO,
                                                                               @RequestParam("page") int page,
                                                                               @RequestParam(value = "size", defaultValue = "12") int size) {
        return new IngredientListResponseDTO<>(
                findIngredientListUseCase.getIngredientList(
                        IngredientSearchCondition.check(mapper.toIngredientSearchCondition(requestDTO, getMemberEmail())), page, size));
    }

    @GetMapping("/api/ingredients/search")
    public IngredientListResponseDTO<IngredientResponseDTO> searchIngredientList() {
        return new IngredientListResponseDTO<>(findIngredientListUseCase.getIngredientListOfAll(getMemberEmail()));
    }

    @GetMapping("/api/ingredients/{ingredientId}")
    public IngredientDetailResponseDTO findIngredient(@PathVariable("ingredientId") Long id) {
        return findIngredientDetailUseCase.getIngredient(id);
    }

    @GetMapping("/api/ingredients/deadline/{days}")
    public IngredientListResponseDTO<IngredientResponseDTO> findIngredientListByDeadline(@PathVariable("days") Long days) {
        return new IngredientListResponseDTO<>(findIngredientListUseCase.getIngredientListByDeadline(days, getMemberEmail()));
    }
}
