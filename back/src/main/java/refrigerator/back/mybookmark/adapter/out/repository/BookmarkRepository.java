package refrigerator.back.mybookmark.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.mybookmark.adapter.out.repository.query.BookmarkQueryRepository;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<MyBookmark, Long>, BookmarkQueryRepository {
    Optional<MyBookmark> findByMemberIdAndRecipeId(String memberId, Long recipeId);
}
