package refrigerator.back.member.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.outbound.dto.MemberCacheDTO;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.outbound.dto.OutMemberDto;


import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface OutMemberMapper {

    OutMemberMapper INSTANCE = Mappers.getMapper(OutMemberMapper.class);
    MemberCacheDTO toMemberCacheDto(Member member);

}
