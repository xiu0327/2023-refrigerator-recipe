package refrigerator.back.mybookmark.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static refrigerator.back.mybookmark.application.domain.QMyBookmark.myBookmark;


@Component
@RequiredArgsConstructor
public class BookmarkUpdateQueryRepositoryImpl implements BookmarkUpdateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public void removeBookmarkByRecipeIdAndMemberId(Long recipeId, String memberId) {
        jpaQueryFactory.update(myBookmark)
                .set(myBookmark.deleted, true)
                .where(myBookmark.recipeId.eq(recipeId), myBookmark.memberId.eq(memberId))
                .execute();
        em.flush();
        em.clear();
    }
}
