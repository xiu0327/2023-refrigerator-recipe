package refrigerator.back.mybookmark.adapter.out.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkDTO;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkPreviewDTO;

import java.util.List;

public interface BookmarkQueryRepository {
    Page<OutBookmarkPreviewDTO> findBookmarkPreview(String memberId, Pageable pageable);
    List<OutBookmarkDTO> findBookmarkList(String memberId, Pageable pageable);
    List<Long> findRecipeIdAddedBookmarks(String memberId);
}
