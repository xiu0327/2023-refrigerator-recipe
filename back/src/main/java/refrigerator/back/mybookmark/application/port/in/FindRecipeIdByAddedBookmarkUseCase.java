package refrigerator.back.mybookmark.application.port.in;

import java.util.List;

public interface FindRecipeIdByAddedBookmarkUseCase {
    List<Long> findRecipeIdList(String memberId);
}
