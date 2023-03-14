package refrigerator.back.member.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import refrigerator.back.member.adapter.out.entity.MemberEntity;
import refrigerator.back.member.application.domain.MemberDomain;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "memberID"),
            @Mapping(source = "memberStatus", target = "memberStatus", defaultValue = "STEADY_STATUS", qualifiedByName = "memberStatusNameToType"),
            @Mapping(source = "profile", target = "profile", defaultValue = "PROFILE_IMAGE_ONE", qualifiedByName = "memberImageNameToType")
    })
    MemberDomain toMemberDomain(MemberEntity memberEntity);

    @Mappings({
            @Mapping(source = "memberID", target = "id"),
            @Mapping(source = "memberStatus", target = "memberStatus", defaultValue = "정상", qualifiedByName = "typeToMemberStatusName"),
            @Mapping(source = "profile", target = "profile",
                    defaultValue = "IMG_9709.JPG",
                    qualifiedByName = "typeToMemberImageName")
    })
    MemberEntity toMemberEntity(MemberDomain memberDomain);

    @Named("memberStatusNameToType")
    default MemberStatus toMemberStatusNameToType(String code){
        return MemberStatus.lookup(code);
    }

    @Named("memberImageNameToType")
    default MemberProfileImage toMemberImageNameToType(String name){
        return MemberProfileImage.findImageByName(name);
    }

    @Named("typeToMemberStatusName")
    default String toTypeToMemberStatusName(MemberStatus type){
        return type.getStatusCode();
    }

    @Named("typeToMemberImageName")
    default String toTypeToMemberImageName(MemberProfileImage type){
        return type.getName();
    }

}
