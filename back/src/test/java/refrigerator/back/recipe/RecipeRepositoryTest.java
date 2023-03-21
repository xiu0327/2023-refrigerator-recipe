package refrigerator.back.recipe;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.out.dto.RecipeListMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeSearchConditionDTO;
import refrigerator.back.recipe.adapter.out.entity.*;
import refrigerator.back.recipe.adapter.out.mapper.RecipeMapper;
import refrigerator.back.recipe.adapter.out.repository.RecipeQueryRepository;
import refrigerator.back.recipe.adapter.out.repository.RecipeSearchQueryRepository;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class RecipeRepositoryTest {

    @Autowired RecipeQueryRepository recipeRepository;
    @Autowired RecipeSearchQueryRepository searchQueryRepository;
    @Autowired RecipeMapper recipeMapper;

    @Test
    void 레시피_단건_조회() {
        Long recipeID = 7L;
        RecipeMappingDTO recipe = recipeRepository.findRecipeByID(recipeID);
        RecipeDomain domain = recipeMapper.toRecipeDomain(recipe);
        assertNotNull(domain.getRecipeID());
        assertNotNull(domain.getRecipeName());
        assertNotNull(domain.getDescription());
        assertNotNull(domain.getCookingTime());
        assertNotNull(domain.getKcal());
        assertNotNull(domain.getServings());
        assertNotNull(domain.getDifficulty());
        assertNotNull(domain.getRecipeType());
        assertNotNull(domain.getRecipeCategory());
        assertNotNull(domain.getImage());
        assertNotNull(domain.getPerson());
        assertNotNull(domain.getScore());
        assertNotNull(domain.getViews());
        assertNotNull(domain.getBookmarks());
        assertNotNull(domain.getIngredients());
        log.info(domain.getRecipeName());
    }

    @Test
    void 레시피_목록_조회(){
        int pageSize = 11;
        List<RecipeDomain> result = recipeRepository.findRecipeList(PageRequest.of(0, pageSize))
                .stream().map(recipeMapper::listDtoToRecipeDomain)
                .collect(Collectors.toList());
        String preName = "";
        for (RecipeDomain recipeDomain : result) {
            log.info("name = {}", recipeDomain.getRecipeName());
            assertThat(recipeDomain.getRecipeName()).isNotEqualTo(preName);
            preName = recipeDomain.getRecipeName();
        }
        assertThat(result.size()).isEqualTo(pageSize);
    }

    @Test
    void 레시피_순서_조회(){
        long recipeID = 1L;
        List<RecipeCourse> course = recipeRepository.findRecipeCourse(recipeID);
        List<RecipeCourseDomain> courseDomainList = course.stream().map(recipeMapper::toCourseDomain)
                .collect(Collectors.toList());

        // 1. 엔티티 모든 필드 값이 null 이 아님
        assertNotNull(course);
        assertThat(course).isNotEmpty();
        for (RecipeCourse recipeCourse : course) {
            assertNotNull(recipeCourse.getCourseID());
            assertNotNull(recipeCourse.getExplanation());
            assertNotNull(recipeCourse.getImage());
            assertNotNull(recipeCourse.getStep());
        }

        // 2. 엔티티 -> 도메인 mapper, 도메인 모든 필드 값이 null 이 아님
        assertNotNull(courseDomainList);
        assertThat(courseDomainList).isNotEmpty();
        for (RecipeCourseDomain recipeCourseDomain : courseDomainList) {
            assertNotNull(recipeCourseDomain.getCourseID());
            assertNotNull(recipeCourseDomain.getExplanation());
            assertNotNull(recipeCourseDomain.getImage());
            assertNotNull(recipeCourseDomain.getStep());
        }
    }

    @Test
    void 레시피_검색(){
        String recipeType = "한식";
        String foodType = "밥";
        String level = "초보환영";
        RecipeSearchConditionDTO dto = RecipeSearchConditionDTO.builder()
                .recipeType(recipeType)
                .recipeFoodType(foodType)
                .difficulty(level).build();
        List<RecipeListMappingDTO> recipes = searchQueryRepository.searchRecipe(dto, PageRequest.of(0, 11));
        for (RecipeListMappingDTO recipe : recipes) {
            log.info("name = {}", recipe.getRecipeName());
            RecipeMappingDTO findRecipe = recipeRepository.findRecipeByID(recipe.getRecipeID());
            assertThat(findRecipe.getRecipeType()).isEqualTo(recipeType);
            assertThat(findRecipe.getRecipeFoodTypeName()).isEqualTo(foodType);
            assertThat(findRecipe.getDifficulty()).isEqualTo(level);
        }
    }
}