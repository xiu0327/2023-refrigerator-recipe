package refrigerator.back.mybookmark.application.port.in;


import refrigerator.back.mybookmark.application.dto.InBookmarkDTO;

import java.util.List;

public interface FindBookmarkListUseCase {
    List<InBookmarkDTO> findBookmarks(String memberId, int page, int size);
}
