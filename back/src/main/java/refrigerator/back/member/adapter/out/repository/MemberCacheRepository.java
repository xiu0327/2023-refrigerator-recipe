package refrigerator.back.member.adapter.out.repository;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.adapter.cache.MemberCacheKey;
import refrigerator.back.member.adapter.mapper.MemberDtoMapper;
import refrigerator.back.member.adapter.out.dto.MemberCacheDTO;
import refrigerator.back.member.application.domain.Member;

import java.util.Optional;

@Repository
@Slf4j
public class MemberCacheRepository {

    private final MemberRepository repository;
    private final MemberDtoMapper memberDtoMapper;
    private final RedisCacheManager redisCacheManager;

    public MemberCacheRepository(MemberRepository repository,
                                 MemberDtoMapper memberDtoMapper,
                                 @Qualifier("memberCacheManager") RedisCacheManager redisCacheManager) {
        this.repository = repository;
        this.memberDtoMapper = memberDtoMapper;
        this.redisCacheManager = redisCacheManager;
    }

    public MemberCacheDTO getCacheData(String email){
        Cache cache = redisCacheManager.getCache(MemberCacheKey.MEMBER);
        MemberCacheDTO cacheData = cache.get(email, MemberCacheDTO.class);
        if (cacheData != null && !cacheData.getEmail().equals("deleted")){
            return cacheData;
        }
        Optional<Member> member = repository.findByEmail(email);
        if (member.isPresent()){
            MemberCacheDTO dto = memberDtoMapper.toMemberCacheDto(member.get());
            cache.put(email, dto);
            return dto;
        }
        return null;
    }

    public void deleteCacheDate(String email){
        Cache cache = redisCacheManager.getCache(MemberCacheKey.MEMBER);
        if (cache != null){
            cache.put(email, new MemberCacheDTO("deleted"));
        }
    }

    public void updateCacheDate(Member member){
        Cache cache = redisCacheManager.getCache(MemberCacheKey.MEMBER);
        if (cache != null){
            MemberCacheDTO dto = memberDtoMapper.toMemberCacheDto(member);
            cache.put(member.getEmail(), dto);
            return ;
        }
        throw new RedisException("캐시를 저장하는 도중 오류가 발생했습니다.");
    }

}
