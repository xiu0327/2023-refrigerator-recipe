package refrigerator.back.word_completion.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.word_completion.application.port.in.RecipeWordCompletionUseCase;

@RestController
@RequiredArgsConstructor
public class RecipeWordCompletionController {

    private final RecipeWordCompletionUseCase recipeWordCompletionUseCase;

    @GetMapping("/api/word-completion/recipe")
    public BasicListResponseDTO<String> getRecipeWordCompletion(@RequestParam("keyword") String keyword){
        return new BasicListResponseDTO<>(recipeWordCompletionUseCase.search(keyword));
    }
}
