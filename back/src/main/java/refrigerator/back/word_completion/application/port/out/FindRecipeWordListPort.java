package refrigerator.back.word_completion.application.port.out;

import java.util.List;

public interface FindRecipeWordListPort {
    List<String> findRecipeNameList();
    List<String> findIngredientNameList();
}
