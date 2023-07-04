package refrigerator.back.comment.outbound.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.outbound.repository.redis.CommentHeartPeopleRedisRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest
class CommentHeartPeopleWriteAdapterTest {

    private CommentHeartPeopleWriteAdapter adapter;
    @Autowired CommentHeartPeopleRedisRepository repository;

    @BeforeEach
    void init(){
        adapter = new CommentHeartPeopleWriteAdapter(repository);
        repository.deleteAll();
    }

    @Test
    @DisplayName("좋아요를 누른 회원 삭제 테스트")
    void remove() {
        // given
        CommentHeartPeople people = new CommentHeartPeople(1L, "email");
        repository.save(people);
        // when
        adapter.remove(people.getId());
        // then
        Optional<CommentHeartPeople> result = repository.findById(people.getId());
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("좋아요를 누른 회원 저장 테스트")
    void save() {
        // given
        CommentHeartPeople people = new CommentHeartPeople(1L, "email");
        // when
        adapter.save(people);
        // then
        Optional<CommentHeartPeople> result = repository.findById(people.getId());
        assertTrue(result.isPresent());
    }

}