package refrigerator.back.mybookmark.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.mybookmark.adapter.out.repository.query.BookmarkQueryRepository;
import refrigerator.back.mybookmark.adapter.out.repository.query.BookmarkUpdateQueryRepository;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.util.Optional;

public interface BookmarkRepository extends
        JpaRepository<MyBookmark, Long>,
        BookmarkQueryRepository,
        BookmarkUpdateQueryRepository {

    Optional<MyBookmark> findByMemberIdAndRecipeId(String memberId, Long recipeId);
    @Query("select b from MyBookmark b where b.bookmarkId= :id and b.deleted=false")
    Optional<MyBookmark> findByBookmarkId(@Param("id") Long id);

    /** batch */

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from MyBookmark mk where mk.deleted = true")
    void deleteMyBookmark();
}
