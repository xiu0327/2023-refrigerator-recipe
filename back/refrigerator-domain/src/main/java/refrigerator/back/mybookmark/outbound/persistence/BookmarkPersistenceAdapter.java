//package refrigerator.back.mybookmark.outbound.persistence;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import refrigerator.back.mybookmark.outbound.repository.jpa.BookmarkRepository;
//import refrigerator.back.mybookmark.application.domain.MyBookmark;
//import refrigerator.back.mybookmark.application.port.out.BookmarkReadPort;
//import refrigerator.back.mybookmark.application.port.out.BookmarkWritePort;
//
//
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class BookmarkPersistenceAdapter implements BookmarkReadPort, BookmarkWritePort {
//
//    private final BookmarkRepository repository;
//
//    @Override
//    public Long save(MyBookmark bookmark) {
//        repository.save(bookmark);
//        return bookmark.getBookmarkId();
//    }
//
//    @Override
//    public Optional<MyBookmark> findBookmarkById(Long bookmarkId) {
//        return repository.findByBookmarkId(bookmarkId);
//    }
//
//    @Override
//    public Optional<MyBookmark> findBookmarkByMemberIdAndRecipeId(String memberId, Long recipeId) {
//        return repository.findByMemberIdAndRecipeId(memberId, recipeId);
//    }
//
//}
