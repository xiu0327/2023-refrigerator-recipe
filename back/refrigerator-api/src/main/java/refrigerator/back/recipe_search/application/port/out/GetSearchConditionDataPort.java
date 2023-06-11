package refrigerator.back.recipe_search.application.port.out;

import java.util.List;

public interface GetSearchConditionDataPort {
    List<String> findRecipeFoodTypeCond();
    List<String> findRecipeCategoryCond();
}
