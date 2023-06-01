package refrigerator.back.mybookmark.application.port.out;


import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewListDTO;

public interface FindBookmarkPreviewListPort {
    InBookmarkPreviewListDTO findBookmarkPreviewList(String memberId, int page, int size);
}
