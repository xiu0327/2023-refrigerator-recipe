package refrigerator.back.word_completion.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.word_completion.application.port.out.FindRecipeWordListPort;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class WordCompletionInit {

    private final FindRecipeWordListPort findRecipeWordListPort;
    private List<String> recipeNames;
    private List<String> ingredientNames;

    @PostConstruct
    public void init(){
        this.recipeNames = findRecipeWordListPort.findRecipeNameList();
        this.ingredientNames = findRecipeWordListPort.findIngredientNameList();
    }

    public Set<String> getRecipeWordCompletionList(){
        Set<String> result = new HashSet<>();
        result.addAll(recipeNames);
        result.addAll(ingredientNames);
        return result;
    }

    public List<String> getIngredientWordCompletionList(){
        return ingredientNames;
    }

}
