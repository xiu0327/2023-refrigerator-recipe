package refrigerator.back.mybookmark.application.port.out;


import refrigerator.back.mybookmark.application.dto.InBookmarkPreviewListDTO;

public interface FindBookmarkPreviewListPort {
    InBookmarkPreviewListDTO findBookmarkPreviewList(String memberId, int page, int size);
}
