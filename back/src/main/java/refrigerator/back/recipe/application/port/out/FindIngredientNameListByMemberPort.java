package refrigerator.back.recipe.application.port.out;

import java.util.List;

public interface FindIngredientNameListByMemberPort {
    List<String> findIngredientNameListByMember(String memberId);
}
