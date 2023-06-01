package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientListResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientUnitResponseDTO;
import refrigerator.back.ingredient.adapter.mapper.IngredientMapper;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.port.in.FindIngredientDetailUseCase;
import refrigerator.back.ingredient.application.port.in.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.MakeImageUrlUseCase;


import java.util.List;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class IngredientLookUpController {

    private final FindIngredientDetailUseCase findIngredientDetailUseCase;
    private final FindIngredientListUseCase findIngredientListUseCase;
    private final FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;
    private final MakeImageUrlUseCase makeImageUrlUseCase;
    private final IngredientMapper mapper;

    // @RequestParam validation 방안 생각해야함

    @GetMapping("/api/ingredients/unit")
    public IngredientUnitResponseDTO findIngredientUnit(@RequestParam(value = "name") String name) {

        return mapper.toIngredientUnitResponseDTO(findRegisteredIngredientUseCase.getIngredient(name));
    }

    @GetMapping("/api/ingredients")
    public IngredientListResponseDTO<IngredientResponseDTO> findIngredientList(@RequestParam(value = "storage", defaultValue = "냉장") String storage,
                                                                               @RequestParam(value = "deadline", defaultValue = "false") boolean deadline,
                                                                               @RequestParam(value = "page") int page,
                                                                               @RequestParam(value = "size", defaultValue = "12") int size) {

        List<IngredientResponseDTO> ingredients = findIngredientListUseCase.getIngredientList(
                mapper.toIngredientSearchCondition(IngredientStorageType.from(storage), deadline, getMemberEmail()), page, size);

        getIngredientResponseDTO(ingredients);

        return new IngredientListResponseDTO<>(ingredients);
    }

    @GetMapping("/api/ingredients/search")
    public IngredientListResponseDTO<IngredientResponseDTO> searchIngredientList() {

        List<IngredientResponseDTO> ingredients = findIngredientListUseCase.getIngredientListOfAll(getMemberEmail());

        getIngredientResponseDTO(ingredients);

        return new IngredientListResponseDTO<>(ingredients);
    }

    @GetMapping("/api/ingredients/{ingredientId}")
    public IngredientDetailResponseDTO findIngredient(@PathVariable("ingredientId") Long id) {

        IngredientDetailResponseDTO ingredient = findIngredientDetailUseCase.getIngredient(id);
        ingredient.setImage(makeImageUrlUseCase.createURL(ingredient.getImage()));

        return ingredient;
    }

    @GetMapping("/api/ingredients/deadline/{days}")
    public IngredientListResponseDTO<IngredientResponseDTO> findIngredientListByDeadline(@PathVariable("days") Long days) {

        List<IngredientResponseDTO> ingredients = findIngredientListUseCase.getIngredientListByDeadline(days, getMemberEmail());

        getIngredientResponseDTO(ingredients);

        return new IngredientListResponseDTO<>(ingredients);
    }

    private void getIngredientResponseDTO(List<IngredientResponseDTO> ingredients) {
        for (IngredientResponseDTO ingredient : ingredients) {
            ingredient.setImage(makeImageUrlUseCase.createURL(ingredient.getImage()));
        }
    }
}
