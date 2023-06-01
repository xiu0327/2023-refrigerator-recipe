package refrigerator.back.recipe;

import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.application.domain.RecipeRecommend;
import refrigerator.back.recipe.application.port.out.FindIngredientNamesPort;
import refrigerator.back.recipe.application.port.out.FindRecommendRecipeInfoPort;
import refrigerator.back.recipe.application.service.RecipeRecommendService;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RecipeRecommendServiceMockTest {

    @InjectMocks RecipeRecommendService service;
    @Mock FindRecommendRecipeInfoPort findRecommendRecipeInfoPort;
    @Mock FindIngredientNamesPort findIngredientNamesPort;

    @Test
    void recommend() {
        String memberId = "email123@gmail.com";
        given(findRecommendRecipeInfoPort.findRecipeIngredientNames())
                .willReturn(getRecipeIngredientData());
        given(findIngredientNamesPort.findIngredientNames(memberId))
                .willReturn(new HashSet<>(Arrays.asList("사과", "배", "계란")));
        given(findRecommendRecipeInfoPort.findRecipeByIds(any()))
                .willReturn(getRecipeInfoData());
        List<InRecipeRecommendDTO> result = service.recommend(memberId);
        assertThat(result.get(0).getMatch()).isEqualTo(100.0);
        assertThat(result.get(1).getMatch()).isEqualTo(60.0);
    }

    @Test
    @DisplayName("사용자가 등록한 식재료가 없을 경우, 에러 발생")
    void recommendFail() {
        String memberId = "notexsit@gmail.com";
        given(findRecommendRecipeInfoPort.findRecipeIngredientNames())
                .willReturn(getRecipeIngredientData());
        given(findIngredientNamesPort.findIngredientNames(memberId))
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
                .set("match", 60.0)
                .sample());
        result.add(sut.giveMeBuilder(InRecipeRecommendDTO.class)
                .set("match", 100.0)
                .sample());
        return result;
    }

    private Map<Long, RecipeRecommend> getRecipeIngredientData() {
        Map<Long, RecipeRecommend> result = new HashMap<>();
        String[][] names = {{"사과", "배", "계란", "빵", "우유"}, {"사과", "배"}};
        long idx = 1L;
        for (String[] name : names) {
            RecipeRecommend recipeRecommend = new RecipeRecommend();
            Arrays.stream(name).forEach(recipeRecommend::addNames);
            result.put(idx++, recipeRecommend);
        }
        return result;
    }
}