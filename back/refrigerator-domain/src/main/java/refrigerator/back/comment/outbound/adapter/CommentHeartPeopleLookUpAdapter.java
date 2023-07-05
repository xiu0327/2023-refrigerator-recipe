package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.outbound.repository.redis.CommentHeartPeopleRedisRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentHeartPeopleLookUpAdapter implements FindCommentHeartPeoplePort {

    private final CommentHeartPeopleRedisRepository redisRepository;

    @Override
    public Map<Long, Object> findPeopleMap(String memberId) {
        List<CommentHeartPeople> peoples = redisRepository.findByMemberId(memberId);
        Map<Long, Object> peoplesMap = new HashMap<>();
        peoples.forEach(people -> peoplesMap.put(people.getCommentId(), people.getId()));
        return peoplesMap;
    }

    @Override
    public Optional<CommentHeartPeople> findPeopleOne(Long commentId, String memberId) {
        return redisRepository.findByCommentIdAndMemberId(commentId, memberId);
    }

    @Override
    public Optional<CommentHeartPeople> findPeopleOneById(String peopleId) {
        return redisRepository.findById(peopleId);
    }
}
