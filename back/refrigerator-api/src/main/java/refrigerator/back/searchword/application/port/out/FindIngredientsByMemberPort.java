package refrigerator.back.searchword.application.port.out;

import java.util.List;

public interface FindIngredientsByMemberPort {
    List<String> getIngredients(String memberId);
}
