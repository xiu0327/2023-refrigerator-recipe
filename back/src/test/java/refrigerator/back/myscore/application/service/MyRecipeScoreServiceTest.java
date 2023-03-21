package refrigerator.back.myscore.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.myscore.adapter.in.dto.CookingResponseDTO;
import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;
import refrigerator.back.myscore.application.domain.MyRecipeScoreListDomain;
import refrigerator.back.myscore.application.port.in.CookingUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScoreListUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScorePreviewUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyRecipeScoreUseCase;
import refrigerator.back.recipe.adapter.out.entity.RecipeScore;

import javax.persistence.EntityManager;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MyRecipeScoreServiceTest {

    @Autowired FindMyRecipeScoreListUseCase findMyRecipeScoreList;
    @Autowired ModifyMyRecipeScoreUseCase modifyMyRecipeScoreUseCase;
    @Autowired FindMyRecipeScorePreviewUseCase findMyRecipeScorePreviewUseCase;
    @Autowired CookingUseCase cookingUseCase;
    @Autowired TestData testData;
    @Autowired EntityManager em;

    @Test
    void 첫_요리하기() {
        // given
        testData.createMember();
        String memberID = TestData.MEMBER_EMAIL;
        Long recipeID = 1L;
        double score = 4.5;
        double beforeScore = em.find(RecipeScore.class, recipeID).getScore();
        int beforePerson = em.find(RecipeScore.class, recipeID).getPerson();
        // when
        CookingResponseDTO cooking = cookingUseCase.cooking(memberID, recipeID, score);
        // then
        MyRecipeScore findScore = em.find(MyRecipeScore.class, cooking.getScoreID());
        RecipeScore afterRecipeScore = em.find(RecipeScore.class, recipeID);
        /* 해당 레시피를 처음 요리했는지 */
        assertThat(cooking.getIsCreated()).isTrue();
        /* 필수 데이터가 제대로 들어갔는지 */
        assertThat(findScore.getRecipeID()).isEqualTo(recipeID);
        assertThat(findScore.getScore()).isEqualTo(score);
        assertThat(findScore.getMemberID()).isEqualTo(memberID);
        /* 레시피 평점이 증가 되었는지 */
        assertThat(afterRecipeScore.getScore()).isEqualTo(beforeScore + score);
        assertThat(afterRecipeScore.getPerson()).isEqualTo(beforePerson + 1);
    }

    @Test
    void 다시_요리하기() {
        // given
        testData.createMember();
        String memberID = TestData.MEMBER_EMAIL;
        Long recipeID = 1L;
        double oldScore = 4.5;
        cookingUseCase.cooking(memberID, recipeID, oldScore);
        int beforePerson = em.find(RecipeScore.class, recipeID).getPerson();
        double beforeScore = em.find(RecipeScore.class, recipeID).getScore();
        // when
        double newScore = 3.5;
        CookingResponseDTO cooking = cookingUseCase.cooking(memberID, recipeID, newScore);
        // then
        MyRecipeScore findScore = em.find(MyRecipeScore.class, cooking.getScoreID());
        int afterPerson = em.find(RecipeScore.class, recipeID).getPerson();
        double afterScore = em.find(RecipeScore.class, recipeID).getScore();
        /* 해당 레시피를 처음 요리했는지 */
        assertThat(cooking.getIsCreated()).isFalse();
        /* 필수 데이터가 존재하는지 */
        assertThat(findScore.getRecipeID()).isEqualTo(recipeID);
        assertThat(findScore.getScore()).isEqualTo(newScore);
        assertThat(findScore.getMemberID()).isEqualTo(memberID);
        /* 다시 요리했을 때, 평점을 남긴 사람 수는 변하지 않음*/
        assertThat(beforePerson).isEqualTo(afterPerson);
        /* 다시 요리했을 때, 평점 = 기존 평점 - old 평점 + new 평점 */
        assertThat(afterScore).isEqualTo(beforeScore - oldScore + newScore);
    }


    @Test
    void 별점_미리보기() {
        // given
        testData.createMember();
        String memberID = TestData.MEMBER_EMAIL;
        double score = 3.5;
        int count = 0;
        for (long recipeID = 1L ; recipeID <= 10L ; recipeID++){
            testData.createMyRecipeScore(memberID, recipeID, score);
            count++;
        }
        // when
        log.info("==== find start ====");
        int previewSize = 5;
        MyRecipeScoreListDomain myScoreList = findMyRecipeScorePreviewUseCase.findPreview(memberID, previewSize);
        log.info("==== find end ====");
        // then
        assertThat(myScoreList.getScores()).isNotEmpty();
        assertThat(myScoreList.getCount()).isEqualTo(count);
        assertThat(myScoreList.getScores().size()).isEqualTo(previewSize);
    }

    @Test
    void 별점_조회(){
        // given
        testData.createMember();
        String memberID = TestData.MEMBER_EMAIL;
        double score = 3.5;
        for (long recipeID = 1L ; recipeID <= 20L ; recipeID++){
            testData.createMyRecipeScore(memberID, recipeID, score);
        }
        // when
        log.info("==== find start ====");
        MyRecipeScoreListDomain myScoreList = findMyRecipeScoreList.findMyScoreList(memberID, 0, 11);
        log.info("==== find end ====");
        // then
        assertThat(myScoreList.getScores()).isNotEmpty();
        assertThat(myScoreList.getScores().size()).isEqualTo(11);
    }

    @Test
    void 별점_수정() {
        // given
        testData.createMember();
        String memberID = TestData.MEMBER_EMAIL;
        Long recipeID = 1L;
        double score = 3.5;
        Long scoreID = testData.createMyRecipeScore(memberID, recipeID, score);
        double beforeScore = em.find(RecipeScore.class, recipeID).getScore();
        int beforePerson = em.find(RecipeScore.class, recipeID).getPerson();
        // when
        double newScore = 2.5;
        System.out.println("MyRecipeScoreServiceTest.별점_수정 - start");
        modifyMyRecipeScoreUseCase.modifyMyRecipeScore(scoreID, newScore);
        System.out.println("MyRecipeScoreServiceTest.별점_수정 - end");
        // then
        MyRecipeScore findMyScore = em.find(MyRecipeScore.class, scoreID);
        RecipeScore findRecipeScore = em.find(RecipeScore.class, recipeID);
        assertThat(findMyScore.getScore()).isEqualTo(newScore);
        assertThat(findRecipeScore.getScore()).isEqualTo(beforeScore + (newScore - score));
        assertThat(findRecipeScore.getPerson()).isEqualTo(beforePerson);
    }
}