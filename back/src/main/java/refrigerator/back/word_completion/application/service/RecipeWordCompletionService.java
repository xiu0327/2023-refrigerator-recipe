package refrigerator.back.word_completion.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.word_completion.application.domain.RecipeNameParserUtils;
import refrigerator.back.word_completion.application.domain.RecipeTrie;
import refrigerator.back.word_completion.application.domain.RecipeTrieNode;
import refrigerator.back.word_completion.application.port.in.RecipeWordCompletionUseCase;
import refrigerator.back.word_completion.application.port.out.FindRecipeNameListPort;

import javax.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeWordCompletionService implements RecipeWordCompletionUseCase {

    private final FindRecipeNameListPort findRecipeNameListPort;
    private final RecipeTrie recipeTrie;

    @PostConstruct
    public void init(){
        List<String> names = findRecipeNameListPort.findNameList();
        RecipeNameParserUtils parser = recipeTrie.getParser();
        for (String name : names) {
            recipeTrie.insert(parser.split(name), name);
        }
    }

    @Override
    public List<String> search(String keyword) {
        RecipeTrieNode currentNode = recipeTrie.getHead();
        List<String> answer = new ArrayList<>();

        String keyword_split = recipeTrie.getParser().split(keyword);

        for (char value : keyword_split.toCharArray()) {
            if (currentNode.getChildren().containsKey(value)){
                currentNode = currentNode.getChildren().get(value);
            }
        }

        if (currentNode.getChildren().isEmpty()){
            answer.add(currentNode.getData());
            return answer;
        }

        Deque<RecipeTrieNode> queue = new ArrayDeque<>();
        queue.add(currentNode);

        while(!queue.isEmpty()){
            RecipeTrieNode node = queue.pollFirst();
            if (node.getChildren().isEmpty()){
                answer.add(node.getData());
            }
            queue.addAll(node.getChildren().values());
        }
        return answer;
    }
}
