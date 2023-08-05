package refrigerator.back.ingredient.application.domain;

import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.MyIngredientDto;
import refrigerator.back.ingredient.application.port.out.ingredient.update.UpdateIngredientVolumePort;

import java.util.List;
import java.util.Map;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.EMPTY_INGREDIENT_LIST;

public class MyIngredientCollection {

    private final Map<String, MyIngredientDto> myIngredients;

    public MyIngredientCollection(Map<String, MyIngredientDto> myIngredients) {
        if (myIngredients.isEmpty()){
            throw new BusinessException(EMPTY_INGREDIENT_LIST);
        }
        this.myIngredients = myIngredients;
    }


    public void deductByRecipeIngredient(List<IngredientDeductionDTO> recipeIngredients){
        recipeIngredients.forEach(recipeIngredient -> {
            MyIngredientDto myIngredient = myIngredients.get(recipeIngredient.getName());
            if (myIngredient != null){
                myIngredient.deduct(recipeIngredient);
            }
        });
    }

    public void updateMyIngredientVolume(UpdateIngredientVolumePort port){
        myIngredients.values().stream()
                .filter(MyIngredientDto::getModifyState)
                .forEach(myIngredient -> port.updateToVolume(myIngredient.getId(), myIngredient.getVolume()));
    }



}
