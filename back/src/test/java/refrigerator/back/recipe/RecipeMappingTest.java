package refrigerator.back.recipe;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import refrigerator.back.global.TestData;
import refrigerator.back.recipe.adapter.in.dto.RecipeCourseDTO;
import refrigerator.back.recipe.adapter.in.dto.RecipeCourseDtoList;
import refrigerator.back.recipe.adapter.in.dto.RecipeDetailDTO;
import refrigerator.back.recipe.adapter.in.web.RecipeDtoMapper;
import refrigerator.back.recipe.adapter.out.entity.Recipe;
import refrigerator.back.recipe.adapter.out.entity.RecipeCourse;
import refrigerator.back.recipe.adapter.out.entity.RecipeIngredient;
import refrigerator.back.recipe.adapter.out.mapper.RecipeMapper;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredientDomain;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class RecipeMappingTest {

    @Autowired TestData testData;
    @Autowired RecipeMapper recipeMapper;
    @Autowired RecipeDtoMapper recipeDtoMapper;

    @Test
    void 레시피_재료_엔티티_To_도메인(){
        RecipeIngredient entity = testData.getRecipeIngredientEntity();
        RecipeIngredientDomain mapperDomain = recipeMapper.toIngredientDomain(entity);

        // 1. 타임명 -> 타입 변경 확인
        assertThat(mapperDomain.getType().getTypeName()).isEqualTo(entity.getType());
        // 2. 모든 필드 값이 null 이어야 함
        assertNotNull(mapperDomain.getIngredientID());
        assertNotNull(mapperDomain.getName());
        assertNotNull(mapperDomain.getVolume());
        assertNotNull(mapperDomain.getTransVolume());
        assertNotNull(mapperDomain.getTransUnit());
        assertNotNull(mapperDomain.getType());
    }

    @Test
    void 레시피_과정_엔티티_To_도메인(){
        RecipeCourse entity = testData.getRecipeCourseEntity();
        RecipeCourseDomain mapperDomain = recipeMapper.toCourseDomain(entity);

        // 모든 필드 값이 null 이어야 함
        assertNotNull(mapperDomain.getCourseID());
        assertNotNull(mapperDomain.getStep());
        assertNotNull(mapperDomain.getExplanation());
        assertNotNull(mapperDomain.getImage());
    }

    @Test
    void 레시피_상세조회_DTO_매핑() {
        RecipeDomain domain = testData.getRecipeDomain();
        RecipeIngredient entity = testData.getRecipeIngredientEntity();
        RecipeIngredientDomain ingredientDomain = recipeMapper.toIngredientDomain(entity);
        Set<RecipeIngredientDomain> tmp = new HashSet<>();
        tmp.add(ingredientDomain);
        domain.initIngredient(tmp);
        domain.calculateScoreAvg();

        RecipeDetailDTO result = recipeDtoMapper.recipeDetailMapper(domain);


        log.info("result = {}", result);

        // 모든 필드 값이 null 이어야 함
        assertNotNull(result.getRecipeID());
        assertNotNull(result.getRecipeName());
        assertNotNull(result.getServings());
        assertNotNull(result.getKcal());
        assertNotNull(result.getDescription());
        assertNotNull(result.getCookingTime());
        assertNotNull(result.getDifficulty());
        assertNotNull(result.getImage());
        assertNotNull(result.getScoreAvg());
        assertNotNull(result.getViews());
        assertNotNull(result.getIngredients());
    }

    @Test
    void 레시피_순서_DTO_매핑(){
        RecipeCourse entity = testData.getRecipeCourseEntity();
        RecipeCourseDomain domain = recipeMapper.toCourseDomain(entity);
        List<RecipeCourseDomain> tmp = new ArrayList<>();
        tmp.add(domain);
        RecipeCourseDtoList courseDtoList = recipeDtoMapper.recipeCourseListMapper(tmp);

        log.info("result = {}", courseDtoList.getData().get(0));

        //모든 필드 값이 null 이어야 함

        for (RecipeCourseDTO recipeCourseDTO : courseDtoList.getData()) {
            assertNotNull(recipeCourseDTO.getExplanation());
            assertNotNull(recipeCourseDTO.getImage());
            assertNotNull(recipeCourseDTO.getStep());
        }
    }

}
