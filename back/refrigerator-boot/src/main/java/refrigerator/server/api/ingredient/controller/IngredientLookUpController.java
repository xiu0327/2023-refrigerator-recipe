//package refrigerator.server.api.ingredient.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import refrigerator.server.api.authentication.GetMemberEmailUseCase;
//import refrigerator.server.api.global.common.BasicListResponseDTO;
//import refrigerator.server.api.ingredient.IngredientImageGenerator;
//import refrigerator.server.api.ingredient.mapper.InIngredientMapper;
//import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
//import refrigerator.back.ingredient.application.dto.IngredientDTO;
//import refrigerator.back.ingredient.application.dto.IngredientUnitDTO;
//import refrigerator.back.ingredient.application.domain.IngredientStorageType;
//import refrigerator.back.ingredient.application.port.in.ingredient.lookUp.FindIngredientUseCase;
//import refrigerator.back.ingredient.application.port.in.ingredient.lookUp.FindIngredientListUseCase;
//import refrigerator.back.ingredient.application.port.in.registeredIngredient.FindRegisteredIngredientUseCase;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class IngredientLookUpController {
//
//    private final FindIngredientUseCase findIngredientDetailUseCase;
//    private final FindIngredientListUseCase findIngredientListUseCase;
//    private final FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;
//
//    private final GetMemberEmailUseCase memberInformation;
//    private final IngredientImageGenerator imageGenerator;
//    private final InIngredientMapper mapper;
//
//    @GetMapping("/api/ingredients/unit")
//    public IngredientUnitDTO findIngredientUnit(@RequestParam(value = "name") String name) {
//
//        return mapper.toIngredientUnitResponseDTO(findRegisteredIngredientUseCase.getIngredient(name));
//    }
//
//    @GetMapping("/api/ingredients")
//    public BasicListResponseDTO<IngredientDTO> findIngredientList(@RequestParam(value = "storage", defaultValue = "냉장") String storage,
//                                                                  @RequestParam(value = "deadline", defaultValue = "false") boolean deadline,
//                                                                  @RequestParam(value = "page") int page,
//                                                                  @RequestParam(value = "size", defaultValue = "12") int size) {
//
//        List<IngredientDTO> ingredientList = findIngredientListUseCase.getIngredientList(
//                mapper.toIngredientSearchCondition(IngredientStorageType.from(storage), deadline,
//                        memberInformation.getMemberEmail()), page, size);
//
//        setImage(ingredientList);
//
//        return new BasicListResponseDTO<>(ingredientList);
//    }
//
//    @GetMapping("/api/ingredients/search")
//    public BasicListResponseDTO<IngredientDTO> searchIngredientList() {
//
//        List<IngredientDTO> ingredientList = findIngredientListUseCase
//                .getIngredientListOfAll(memberInformation.getMemberEmail());
//
//        setImage(ingredientList);
//
//        return new BasicListResponseDTO<>(ingredientList);
//    }
//
//    @GetMapping("/api/ingredients/{ingredientId}")
//    public IngredientDetailDTO findIngredient(@PathVariable("ingredientId") Long id) {
//
//        IngredientDetailDTO ingredient = findIngredientDetailUseCase.getIngredient(id);
//        ingredient.generateImageUrl(imageGenerator);
//
//        return ingredient;
//    }
//
//    @GetMapping("/api/ingredients/deadline/{days}")
//    public BasicListResponseDTO<IngredientDTO> findIngredientListByDeadline(@PathVariable("days") Long days) {
//
//        List<IngredientDTO> ingredientList = findIngredientListUseCase
//                .getIngredientListByDeadline(days, memberInformation.getMemberEmail());
//
//        setImage(ingredientList);
//
//        return new BasicListResponseDTO<>(ingredientList);
//    }
//
//    private void setImage(List<IngredientDTO> ingredientList) {
//        ingredientList.forEach(dto -> dto.generateImageUrl(imageGenerator));
//    }
//}
