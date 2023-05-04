package refrigerator.back.mybookmark.application.port.out;

import java.util.List;

public interface FindRecipeIdAddedBookmarkPort {
    List<Long> findRecipeIdByAddedBookmark(String memberId);
}
