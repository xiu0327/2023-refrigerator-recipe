package refrigerator.back.mybookmark.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkListDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewListDTO;
import refrigerator.back.mybookmark.application.domain.MyBookmark;
import refrigerator.back.mybookmark.application.port.in.AddBookmarkUseCase;
import refrigerator.back.mybookmark.application.port.in.FindBookmarkListUseCase;
import refrigerator.back.mybookmark.application.port.in.FindBookmarkPreviewUseCase;
import refrigerator.back.mybookmark.application.port.in.RemoveBookmarkUseCase;
import refrigerator.back.mybookmark.application.port.out.BookmarkReadPort;
import refrigerator.back.mybookmark.application.port.out.BookmarkWritePort;
import refrigerator.back.mybookmark.exception.MyBookmarkExceptionType;
import refrigerator.back.recipe.application.port.out.UpdateRecipeBookmarkPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyBookmarkService implements AddBookmarkUseCase, RemoveBookmarkUseCase,
        FindBookmarkPreviewUseCase, FindBookmarkListUseCase {

    private final BookmarkWritePort bookmarkWritePort;
    private final BookmarkReadPort bookmarkReadPort;
    private final UpdateRecipeBookmarkPort updateRecipeBookmarkPort;

    @Override
    @Transactional
    public Long add(String memberId, Long recipeId) {
        Optional<MyBookmark> findMyBookmark =
                bookmarkReadPort.findBookmarkByMemberIdAndRecipeId(memberId, recipeId);
        if (findMyBookmark.isPresent()){
            MyBookmark myBookmark = findMyBookmark.get();
            if (!myBookmark.isDeleted()){
                throw new BusinessException(MyBookmarkExceptionType.ALREADY_ADD_BOOKMARK);
            }
            myBookmark.undeleted();
            updateRecipeBookmarkPort.addBookmark(recipeId);
            return myBookmark.getRecipeId();
        }
        MyBookmark myBookmark = MyBookmark.create(memberId, recipeId);
        bookmarkWritePort.save(myBookmark);
        updateRecipeBookmarkPort.addBookmark(recipeId);
        return myBookmark.getBookmarkId();
    }

    @Override
    @Transactional(readOnly = true)
    public InBookmarkListDTO findBookmarks(String memberId, int page, int size) {
        return InBookmarkListDTO.builder()
                .bookmarks(bookmarkReadPort.findBookmarkList(memberId, page, size)).build();
    }

    @Override
    @Transactional(readOnly = true)
    public InBookmarkPreviewListDTO findPreviews(String memberId, int size) {
        List<InBookmarkPreviewDTO> bookmarks = bookmarkReadPort.findBookmarkPreviewList(memberId);
        return InBookmarkPreviewListDTO.builder()
                .bookmarks(bookmarks.stream()
                        .limit(size)
                        .collect(Collectors.toList()))
                .count(bookmarks.size())
                .build();
    }

    @Override
    @Transactional
    public void remove(Long bookmarkId) {
        MyBookmark myBookmark = bookmarkReadPort.findBookmarkById(bookmarkId)
                .orElseThrow(() -> new BusinessException(MyBookmarkExceptionType.NOT_FOUND_BOOKMARK));
        myBookmark.delete();
        updateRecipeBookmarkPort.removeBookmark(myBookmark.getRecipeId());
    }
}
