package refrigerator.back.recipe_recommend.application;

import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe_recommend.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.application.domain.RecipeIngredientTuple;
import refrigerator.back.recipe_recommend.application.port.out.GetMyIngredientNameDataPort;
import refrigerator.back.recipe_recommend.application.port.out.GetRecipeRecommendInfoDataPort;
import refrigerator.back.recipe_recommend.application.service.RecipeRecommendService;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RecipeRecommendServiceMockTest {

    @InjectMocks RecipeRecommendService service;
    @Mock
    GetMyIngredientNameDataPort findMyIngredientNames;
    @Mock
    GetRecipeRecommendInfoDataPort findRecipeRecommendInfo;

    @Test
    @DisplayName("레시피 추천 성공")
    void recommend() {
        String memberId = "email123@gmail.com";
        given(findMyIngredientNames.findMyIngredientNames(memberId))
                .willReturn(new HashSet<>(Arrays.asList("사과", "계란", "배")));
        given(findRecipeRecommendInfo.findRecipeIngredientNames())
                .willReturn(getRecipeIngredientNames());
        given(findRecipeRecommendInfo.findInfoByIds(any()))
                .willReturn(getRecipeInfoData());
        List<InRecipeRecommendDTO> result = service.recommend(memberId);
        assertThat(result.get(0).getMatch()).isEqualTo(100.0);
        assertThat(result.get(1).getMatch()).isEqualTo(62.5);
    }

    @Test
    @DisplayName("사용자가 등록한 식재료가 없을 경우, 에러 발생")
    void recommendFail() {
        String memberId = "notexsit@gmail.com";
        given(findMyIngredientNames.findMyIngredientNames(memberId))
                .willReturn(new HashSet<>());
        assertThrows(BusinessException.class, () -> {
            try{
                service.recommend(memberId);
            }catch(BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(RecipeExceptionType.EMPTY_MEMBER_INGREDIENT);
                throw e;
            }
        });
    }

    private List<InRecipeRecommendDTO> getRecipeInfoData() {
        FixtureMonkey sut = FixtureMonkey.create();
        List<InRecipeRecommendDTO> result = new ArrayList<>();
        result.add(sut.giveMeBuilder(InRecipeRecommendDTO.class)
                .set("recipeID", 1L)
                .set("match", 0.0)
                .sample());
        result.add(sut.giveMeBuilder(InRecipeRecommendDTO.class)
                .set("recipeID", 2L)
                .set("match", 0.0)
                .sample());
        return result;
    }

    private Map<Long, Set<RecipeIngredientTuple>> getRecipeIngredientNames() {
        Map<Long, Set<RecipeIngredientTuple>> data = new HashMap<>();
        // given 1
        Set<RecipeIngredientTuple> firstTuple = new HashSet<>();
        firstTuple.add(new RecipeIngredientTuple("사과", "주재료"));
        firstTuple.add(new RecipeIngredientTuple("계란", "부재료"));
        firstTuple.add(new RecipeIngredientTuple("케찹", "양념"));
        firstTuple.add(new RecipeIngredientTuple("소금", "양념"));
        firstTuple.add(new RecipeIngredientTuple("후추", "양념"));
        data.put(1L, firstTuple);
        // given 2
        Set<RecipeIngredientTuple> secondTuple = new HashSet<>();
        secondTuple.add(new RecipeIngredientTuple("사과", "주재료"));
        secondTuple.add(new RecipeIngredientTuple("배", "주재료"));
        data.put(2L, secondTuple);
        return data;
    }
}