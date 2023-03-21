package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.SaveIngredientUseCase;

@RestController
@RequiredArgsConstructor
public class IngredientController {

    final private ModifyIngredientUseCase modifyIngredientUseCase;
    final private RemoveIngredientUseCase removeIngredientUseCase;
    final private SaveIngredientUseCase saveIngredientUseCase;

    @PostMapping("/api/ingredient/{ingredientId")
    public void save() {

    }

    @PutMapping("/api/ingredient/{ingredientId")
    public void update() {

    }

    @DeleteMapping()
    public void remove() {

    }
}
