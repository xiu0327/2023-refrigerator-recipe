package refrigerator.back.mybookmark.application.port.in;


import refrigerator.back.mybookmark.application.dto.BookmarkDto;

import java.util.List;

public interface FindBookmarkListUseCase {
    List<BookmarkDto> findBookmarks(String memberId, int page, int size);
}
