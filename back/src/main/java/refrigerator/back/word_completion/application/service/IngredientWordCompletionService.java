package refrigerator.back.word_completion.application.service;

import org.springframework.stereotype.Service;
import refrigerator.back.word_completion.application.domain.WordCompletionTrie;
import refrigerator.back.word_completion.application.domain.WordFormat;
import refrigerator.back.word_completion.application.port.in.IngredientWordCompletionUseCase;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientWordCompletionService implements IngredientWordCompletionUseCase {

    private final WordCompletionInit wordCompletionInit;
    private final WordCompletionTrie ingredientTrie;
    private final WordFormat wordFormat;

    public IngredientWordCompletionService(WordCompletionInit wordCompletionInit) {
        this.wordCompletionInit = wordCompletionInit;
        this.ingredientTrie = new WordCompletionTrie();
        this.wordFormat = new WordFormat();
    }

    @PostConstruct
    public void init(){
        List<String> words = wordCompletionInit.getIngredientWordCompletionList();
        words.forEach(ingredientTrie::insert);
    }

    @Override
    public List<String> search(String keyword) {
        if (!wordFormat.wordCheck(keyword)){
            return new ArrayList<>();
        }
        return ingredientTrie.search(keyword);
    }
}
