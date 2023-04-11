package refrigerator.back.word_completion.application.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RecipeTrie {

    private final RecipeNameParserUtils parser;
    private final RecipeTrieNode head = new RecipeTrieNode(null);

    public RecipeTrieNode getHead() {
        return head;
    }

    public RecipeNameParserUtils getParser() {
        return parser;
    }

    public void insert(String recipeName, String originalRecipeName){
        RecipeTrieNode currentNode = this.head;
        for (char value : recipeName.toCharArray()) {
            if (!currentNode.getChildren().containsKey(value)){
                currentNode.getChildren().put(value, new RecipeTrieNode(value));
            }
            currentNode = currentNode.getChildren().get(value);
        }
        currentNode.setData(originalRecipeName);
    }
}
