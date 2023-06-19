package refrigerator.back.mybookmark.application.port.in;


import refrigerator.back.mybookmark.application.dto.InBookmarkPreviewListDTO;

public interface FindBookmarkPreviewUseCase {
    InBookmarkPreviewListDTO findPreviews(String memberId, int size);
}
