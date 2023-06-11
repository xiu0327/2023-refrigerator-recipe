package refrigerator.back.recipe_searchword.adapter.out;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SearchWordAdapterTest {

    @Autowired
    LastSearchWordAdapter searchWordAdapter;

    @Test
    @DisplayName("검색어 저장")
    void add() {
        searchWordAdapter.add("test", "검색어");
    }

    @Test
    void deleteOldWord() {
        searchWordAdapter.deleteOldWord("test");
    }

    @Test
    void delete() {
        searchWordAdapter.delete("test", "r");
    }

    @Test
    void findSearchWord() {
        searchWordAdapter.findSearchWord("test");
    }

    @Test
    void getWordListSize() {
        searchWordAdapter.getWordListSize("test");
    }
}