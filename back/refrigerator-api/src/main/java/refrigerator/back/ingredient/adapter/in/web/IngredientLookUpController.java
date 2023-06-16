package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.api.BasicListResponseDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientUnitDTO;
import refrigerator.back.ingredient.adapter.in.mapper.InIngredientMapper;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.port.in.FindIngredientDetailUseCase;
import refrigerator.back.ingredient.application.port.in.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.MakeImageUrlUseCase;


import java.util.List;

import static refrigerator.back.global.common.api.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class IngredientLookUpController {

    private final FindIngredientDetailUseCase findIngredientDetailUseCase;
    private final FindIngredientListUseCase findIngredientListUseCase;
    private final FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;
    private final MakeImageUrlUseCase makeImageUrlUseCase;
    private final InIngredientMapper mapper;

    // @RequestParam validation 방안 생각해야함

    @GetMapping("/api/ingredients/unit")
    public IngredientUnitDTO findIngredientUnit(@RequestParam(value = "name") String name) {

        return mapper.toIngredientUnitResponseDTO(findRegisteredIngredientUseCase.getIngredient(name));
    }

    @GetMapping("/api/ingredients")
    public BasicListResponseDTO<IngredientDTO> findIngredientList(@RequestParam(value = "storage", defaultValue = "냉장") String storage,
                                                                  @RequestParam(value = "deadline", defaultValue = "false") boolean deadline,
                                                                  @RequestParam(value = "page") int page,
                                                                  @RequestParam(value = "size", defaultValue = "12") int size) {

        List<IngredientDTO> ingredients = findIngredientListUseCase.getIngredientList(
                mapper.toIngredientSearchCondition(IngredientStorageType.from(storage), deadline, getMemberEmail()), page, size);

        getIngredientResponseDTO(ingredients);

        return new BasicListResponseDTO<>(ingredients);
    }

    @GetMapping("/api/ingredients/search")
    public BasicListResponseDTO<IngredientDTO> searchIngredientList() {

        List<IngredientDTO> ingredients = findIngredientListUseCase.getIngredientListOfAll(getMemberEmail());

        getIngredientResponseDTO(ingredients);

        return new BasicListResponseDTO<>(ingredients);
    }

    @GetMapping("/api/ingredients/{ingredientId}")
    public IngredientDetailDTO findIngredient(@PathVariable("ingredientId") Long id) {

        IngredientDetailDTO ingredient = findIngredientDetailUseCase.getIngredient(id);
        ingredient.setImage(makeImageUrlUseCase.createURL(ingredient.getImage()));

        return ingredient;
    }

    @GetMapping("/api/ingredients/deadline/{days}")
    public BasicListResponseDTO<IngredientDTO> findIngredientListByDeadline(@PathVariable("days") Long days) {

        List<IngredientDTO> ingredients = findIngredientListUseCase.getIngredientListByDeadline(days, getMemberEmail());

        getIngredientResponseDTO(ingredients);

        return new BasicListResponseDTO<>(ingredients);
    }

    private void getIngredientResponseDTO(List<IngredientDTO> ingredients) {
        for (IngredientDTO ingredient : ingredients) {
            ingredient.setImage(makeImageUrlUseCase.createURL(ingredient.getImage()));
        }
    }
}
