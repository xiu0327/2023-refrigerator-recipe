package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientListResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientUnitResponseDTO;
import refrigerator.back.ingredient.adapter.mapper.IngredientMapper;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.port.in.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.FindIngredientDetailUseCase;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class IngredientLookUpController {

    private final FindIngredientDetailUseCase findIngredientDetailUseCase;
    private final FindIngredientListUseCase findIngredientListUseCase;
    private final FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;
    private final IngredientMapper mapper;

    // @RequestParam validation 방안 생각해야함

    @GetMapping("/api/ingredients/unit")
    public IngredientUnitResponseDTO findIngredientUnit(@RequestParam(value = "name") String name) {

        return mapper.toIngredientUnitResponseDTO(findRegisteredIngredientUseCase.getIngredient(name));
    }

    @GetMapping("/api/ingredients")
    public IngredientListResponseDTO<IngredientResponseDTO> findIngredientList( @RequestParam(value = "storage", defaultValue = "냉장") String storage,
                                                                                @RequestParam(value = "deadline", defaultValue = "false") boolean deadline,
                                                                                @RequestParam(value = "page") int page,
                                                                                @RequestParam(value = "size", defaultValue = "12") int size) {
        
        return new IngredientListResponseDTO<>(
                findIngredientListUseCase.getIngredientList(
                        mapper.toIngredientSearchCondition(
                                IngredientStorageType.from(storage), deadline, getMemberEmail()), page, size));
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
