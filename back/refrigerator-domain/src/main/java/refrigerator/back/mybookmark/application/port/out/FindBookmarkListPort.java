package refrigerator.back.mybookmark.application.port.out;


import refrigerator.back.mybookmark.application.dto.BookmarkDto;

import java.util.List;

public interface FindBookmarkListPort {
    List<BookmarkDto> findBookmarkList(String memberId, int page, int size);
}
