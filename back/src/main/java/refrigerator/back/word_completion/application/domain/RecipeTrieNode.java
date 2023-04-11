package refrigerator.back.word_completion.application.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RecipeTrieNode {

    private final Character key;
    private String data;
    private final Map<Character, RecipeTrieNode> children = new HashMap<>();

    public RecipeTrieNode(Character key) {
        this.key = key;
        this.data = null;
    }

    public void setData(String data) {
        this.data = data;
    }

}
