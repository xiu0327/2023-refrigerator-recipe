package refrigerator.back.mybookmark.application.port.in;


import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkListDTO;

public interface FindBookmarkListUseCase {
    InBookmarkListDTO findBookmarks(String memberId, int page, int size);
}
