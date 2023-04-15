package refrigerator.back.word_completion.application.service;

import org.springframework.stereotype.Service;
import refrigerator.back.word_completion.application.domain.WordCompletionTrie;
import refrigerator.back.word_completion.application.port.in.RecipeWordCompletionUseCase;
import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RecipeWordCompletionService implements RecipeWordCompletionUseCase {

    private final WordCompletionInit wordCompletionInit;
    private WordCompletionTrie recipeTrie;

    public RecipeWordCompletionService(WordCompletionInit wordCompletionInit) {
        this.wordCompletionInit = wordCompletionInit;
        this.recipeTrie = new WordCompletionTrie();
    }

    @PostConstruct
    public void init(){
        Set<String> words = wordCompletionInit.getRecipeWordCompletionList();
        words.forEach(recipeTrie::insert);
    }

    @Override
    public List<String> search(String keyword) {
        return recipeTrie.search(keyword);
    }
}
