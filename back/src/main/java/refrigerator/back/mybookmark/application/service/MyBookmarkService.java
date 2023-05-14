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
import refrigerator.back.mybookmark.application.port.in.*;
import refrigerator.back.mybookmark.application.port.out.BookmarkReadPort;
import refrigerator.back.mybookmark.application.port.out.BookmarkWritePort;
import refrigerator.back.mybookmark.application.port.out.RemoveBookmarkByRecipeIdPort;
import refrigerator.back.mybookmark.exception.MyBookmarkExceptionType;
import refrigerator.back.recipe.application.port.out.UpdateRecipeBookmarkPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyBookmarkService implements AddBookmarkUseCase, RemoveBookmarkUseCase{

    private final BookmarkWritePort bookmarkWritePort;
    private final BookmarkReadPort bookmarkReadPort;
    private final UpdateRecipeBookmarkPort updateRecipeBookmarkPort;
    private final RemoveBookmarkByRecipeIdPort removeBookmarkByRecipeIdPort;

    @Override
    @Transactional
    public Long add(String memberId, Long recipeId) {
        Optional<MyBookmark> findMyBookmark = bookmarkReadPort.findBookmarkByMemberIdAndRecipeId(memberId, recipeId);
        if (findMyBookmark.isPresent()){
            return renewalBookmark(recipeId, findMyBookmark.get());
        }
        return createBookmark(memberId, recipeId);
    }

    private Long createBookmark(String memberId, Long recipeId) {
        MyBookmark myBookmark = MyBookmark.create(memberId, recipeId);
        bookmarkWritePort.save(myBookmark);
        updateRecipeBookmarkPort.addBookmark(recipeId);
        return myBookmark.getBookmarkId();
    }

    private Long renewalBookmark(Long recipeId, MyBookmark myBookmark) {
        if (!myBookmark.isDeleted()){
            throw new BusinessException(MyBookmarkExceptionType.ALREADY_ADD_BOOKMARK);
        }
        Long bookmarkId = myBookmark.getBookmarkId();
        myBookmark.undeleted();
        updateRecipeBookmarkPort.addBookmark(recipeId);
        return bookmarkId;
    }

    @Override
    @Transactional
    public void remove(Long bookmarkId) {
        MyBookmark myBookmark = bookmarkReadPort.findBookmarkById(bookmarkId)
                .orElseThrow(() -> new BusinessException(MyBookmarkExceptionType.ALREADY_DELETE_BOOKMARK));
        myBookmark.delete();
        updateRecipeBookmarkPort.removeBookmark(myBookmark.getRecipeId());
    }

    @Override
    @Transactional
    public void removeByRecipeId(Long recipeId, String memberId) {
        MyBookmark myBookmark = bookmarkReadPort.findBookmarkByMemberIdAndRecipeId(memberId, recipeId)
                .orElseThrow(() -> new BusinessException(MyBookmarkExceptionType.NOT_FOUND_BOOKMARK));
        if (myBookmark.isDeleted()){
            throw new BusinessException(MyBookmarkExceptionType.ALREADY_DELETE_BOOKMARK);
        }
        myBookmark.delete();
        updateRecipeBookmarkPort.removeBookmark(recipeId);
    }

}
