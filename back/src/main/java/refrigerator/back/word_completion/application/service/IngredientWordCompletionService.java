package refrigerator.back.word_completion.application.service;

import org.springframework.stereotype.Service;
import refrigerator.back.word_completion.application.domain.WordCompletionTrie;
import refrigerator.back.word_completion.application.port.in.IngredientWordCompletionUseCase;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class IngredientWordCompletionService implements IngredientWordCompletionUseCase {

    private final WordCompletionInit wordCompletionInit;
    private final WordCompletionTrie ingredientTrie;

    public IngredientWordCompletionService(WordCompletionInit wordCompletionInit) {
        this.wordCompletionInit = wordCompletionInit;
        this.ingredientTrie = new WordCompletionTrie();
    }

    @PostConstruct
    public void init(){
        List<String> words = wordCompletionInit.getIngredientWordCompletionList();
        words.forEach(ingredientTrie::insert);
    }

    @Override
    public List<String> search(String keyword) {
        return ingredientTrie.search(keyword);
    }
}
