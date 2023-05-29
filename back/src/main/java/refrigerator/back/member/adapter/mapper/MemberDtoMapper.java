package refrigerator.back.member.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.member.adapter.out.dto.MemberCacheDTO;
import refrigerator.back.member.application.domain.Member;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface MemberDtoMapper {

    MemberDtoMapper INSTANCE = Mappers.getMapper(MemberDtoMapper.class);

    Member toMember(MemberCacheDTO dto, LocalDateTime createDate);
    MemberCacheDTO toMemberCacheDto(Member member);
}
