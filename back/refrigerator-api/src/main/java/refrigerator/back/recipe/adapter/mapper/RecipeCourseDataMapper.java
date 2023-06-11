package refrigerator.back.recipe.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.adapter.in.dto.InRecipeCourseDto;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;

@Mapper(componentModel = "spring")
public interface RecipeCourseDataMapper {

    RecipeBasicDataMapper INSTANCE = Mappers.getMapper(RecipeBasicDataMapper.class);

    InRecipeCourseDto toInRecipeCourseDto(RecipeCourse recipeCourse);
}
