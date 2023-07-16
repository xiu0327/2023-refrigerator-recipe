package refrigerator.back.mybookmark.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.mybookmark.application.domain.MyBookmark;
import refrigerator.back.mybookmark.application.port.out.FindMyBookmarkPort;
import refrigerator.back.mybookmark.outbound.repository.jpa.MyBookmarkJpaRepository;

@Repository
@RequiredArgsConstructor
public class MyBookmarkPersistenceAdapter implements FindMyBookmarkPort {

    private final MyBookmarkJpaRepository myBookmarkRepository;

    @Override
    public MyBookmark getMyBookmark(Long recipeId, String memberId) {
        return myBookmarkRepository.findByRecipeIdAndMemberId(recipeId, memberId);
    }

}
