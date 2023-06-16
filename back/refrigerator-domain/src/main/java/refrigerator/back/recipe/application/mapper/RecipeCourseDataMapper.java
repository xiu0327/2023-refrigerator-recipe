package refrigerator.back.recipe.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.domain.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;

@Mapper(componentModel = "spring")
public interface RecipeCourseDataMapper {

    RecipeBasicDataMapper INSTANCE = Mappers.getMapper(RecipeBasicDataMapper.class);

    RecipeCourseDto toInRecipeCourseDto(RecipeCourse recipeCourse);
}
