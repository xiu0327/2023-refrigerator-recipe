package refrigerator.back.mybookmark.application.port.out;


import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.util.Optional;

public interface BookmarkReadPort {
    Optional<MyBookmark> findBookmarkById(Long bookmarkId);
    Optional<MyBookmark> findBookmarkByMemberIdAndRecipeId(String memberId, Long recipeId);
}
