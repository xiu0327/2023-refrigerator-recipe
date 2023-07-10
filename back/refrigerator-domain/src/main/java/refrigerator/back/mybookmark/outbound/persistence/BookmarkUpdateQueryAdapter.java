//package refrigerator.back.mybookmark.outbound.persistence;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import refrigerator.back.mybookmark.outbound.repository.jpa.BookmarkRepository;
//import refrigerator.back.mybookmark.application.port.out.RemoveBookmarkByRecipeIdPort;
//
//
//@Component
//@RequiredArgsConstructor
//public class BookmarkUpdateQueryAdapter implements RemoveBookmarkByRecipeIdPort {
//
//    private final BookmarkRepository repository;
//
//    @Override
//    public void removeByRecipeId(Long recipeId, String memberId) {
//        repository.removeBookmarkByRecipeIdAndMemberId(recipeId, memberId);
//    }
//}
