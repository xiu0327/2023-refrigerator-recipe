package refrigerator.back.mybookmark.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.mybookmark.adapter.in.cache.MyBookmarkCacheKey;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkListDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewListDTO;
import refrigerator.back.mybookmark.application.port.in.FindBookmarkListUseCase;
import refrigerator.back.mybookmark.application.port.in.FindBookmarkPreviewUseCase;
import refrigerator.back.mybookmark.application.port.in.FindRecipeIdByAddedBookmarkUseCase;
import refrigerator.back.mybookmark.application.port.out.FindBookmarkListPort;
import refrigerator.back.mybookmark.application.port.out.FindBookmarkPreviewListPort;
import refrigerator.back.mybookmark.application.port.out.FindRecipeIdAddedBookmarkPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyBookmarkLookUpService implements FindBookmarkPreviewUseCase, FindBookmarkListUseCase, FindRecipeIdByAddedBookmarkUseCase {

    private final FindBookmarkListPort findBookmarkListPort;
    private final FindBookmarkPreviewListPort findBookmarkPreviewListPort;
    private final FindRecipeIdAddedBookmarkPort findRecipeIdAddedBookmarkPort;

    @Override
    @Transactional(readOnly = true)
    public InBookmarkListDTO findBookmarks(String memberId, int page, int size) {
        return InBookmarkListDTO.builder()
                .bookmarks(findBookmarkListPort.findBookmarkList(memberId, page, size)).build();
    }

    @Override
    @Transactional(readOnly = true)
    public InBookmarkPreviewListDTO findPreviews(String memberId, int size) {
        return findBookmarkPreviewListPort.findBookmarkPreviewList(memberId, 0, size);
    }

    @Override
    @Cacheable(value = MyBookmarkCacheKey.ADDED_MY_BOOKMARK, key = "'bookmark_' + #memberId", cacheManager = "addedMyBookmarkCacheManager")
    public List<Long> findRecipeIdList(String memberId) {
        return findRecipeIdAddedBookmarkPort.findRecipeIdByAddedBookmark(memberId);
    }
}
