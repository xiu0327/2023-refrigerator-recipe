package refrigerator.back.mybookmark.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.mybookmark.adapter.out.repository.BookmarkRepository;
import refrigerator.back.mybookmark.application.port.out.RemoveBookmarkByRecipeIdPort;


@Component
@RequiredArgsConstructor
public class BookmarkUpdateQueryAdapter implements RemoveBookmarkByRecipeIdPort {

    private final BookmarkRepository repository;

    @Override
    public void removeByRecipeId(Long recipeId, String memberId) {
        repository.removeBookmarkByRecipeIdAndMemberId(recipeId, memberId);
    }
}
