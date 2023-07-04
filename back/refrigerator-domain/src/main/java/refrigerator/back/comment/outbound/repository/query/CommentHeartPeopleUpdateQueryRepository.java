package refrigerator.back.comment.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentHeartPeopleUpdateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public long updateToCommentHeartPeopleDeletedStatus(Long peopleId){
        return 1L;
    }


}
