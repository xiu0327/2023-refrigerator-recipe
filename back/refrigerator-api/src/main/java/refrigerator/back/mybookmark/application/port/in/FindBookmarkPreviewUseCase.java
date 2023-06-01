package refrigerator.back.mybookmark.application.port.in;


import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewListDTO;

public interface FindBookmarkPreviewUseCase {
    InBookmarkPreviewListDTO findPreviews(String memberId, int size);
}
