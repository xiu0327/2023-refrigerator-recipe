package refrigerator.back.searchword.adapter.out;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import refrigerator.back.searchword.application.port.out.*;

import java.util.List;

@Repository
public class LastSearchWordAdapter implements
        AddSearchWordPort, DeleteOldSearchWordPort, DeleteSearchWordPort,
        FindSearchWordPort, GetSearchWordListSizePort {

    private final ListOperations<String, String> searchWordRedisTemplate;

    public LastSearchWordAdapter(
            @Qualifier("searchWordRedisTemplate") RedisTemplate<String, String> searchWordRedisTemplate) {
        this.searchWordRedisTemplate = searchWordRedisTemplate.opsForList();
    }

    @Override
    public void add(String key, String searchWord) {
        searchWordRedisTemplate.leftPush(key, searchWord);
    }

    @Override
    public void deleteOldWord(String key) {
        searchWordRedisTemplate.rightPop(key);
    }

    @Override
    public void delete(String key, String deleteValue) {
        searchWordRedisTemplate.remove(key, 1, deleteValue);
    }

    @Override
    public List<String> findSearchWord(String key) {
        return searchWordRedisTemplate.range(key, 0, -1);
    }

    @Override
    public Long getWordListSize(String key) {
        return searchWordRedisTemplate.size(key);
    }
}
