package refrigerator.back.myscore.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;
import refrigerator.back.myscore.application.port.in.AssessMyRecipeScoreUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScoreListUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyRecipeScoreUseCase;
import refrigerator.back.recipe.adapter.out.entity.RecipeScore;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MyRecipeScoreServiceTest {

    @Autowired AssessMyRecipeScoreUseCase assessMyRecipeScoreUseCase;
    @Autowired
    FindMyRecipeScoreListUseCase findMyRecipeScoreList;
    @Autowired ModifyMyRecipeScoreUseCase modifyMyRecipeScoreUseCase;
    @Autowired TestData testData;
    @Autowired EntityManager em;

    @Test
    void 별점_등록() {
        // given
        testData.createMember();
        String memberID = TestData.MEMBER_EMAIL;
        Long recipeID = 1L;
        double score = 4.5;
        double beforeScore = em.find(RecipeScore.class, recipeID).getScore();
        int beforePerson = em.find(RecipeScore.class, recipeID).getPerson();
        // when
        Long scoreID = assessMyRecipeScoreUseCase.assessRecipeScore(memberID, recipeID, score);
        // then
        MyRecipeScore findScore = em.find(MyRecipeScore.class, scoreID);
        RecipeScore afterRecipeScore = em.find(RecipeScore.class, recipeID);
        assertThat(findScore.getRecipeID()).isEqualTo(recipeID);
        assertThat(findScore.getScore()).isEqualTo(score);
        assertThat(findScore.getMemberID()).isEqualTo(memberID);
        assertThat(afterRecipeScore.getScore()).isEqualTo(beforeScore + score);
        assertThat(afterRecipeScore.getPerson()).isEqualTo(beforePerson + 1);
    }

    @Test
    void 별점_조회() {
        // given
        List<Long> result = new ArrayList<>();
        testData.createMember();
        String memberID = TestData.MEMBER_EMAIL;
        double score = 3.5;
        for (long recipeID = 1L ; recipeID <= 5L ; recipeID++){
            Long scoreID = assessMyRecipeScoreUseCase.assessRecipeScore(memberID, recipeID, score);
            result.add(scoreID);
        }
        // when
        List<Long> myScoreList = findMyRecipeScoreList.findMyScoreList(memberID, 0, 5)
                .stream().map(MyRecipeScoreDomain::getScoreID)
                .collect(Collectors.toList());
        // then
        assertThat(myScoreList).isNotEmpty();
        assertThat(myScoreList.size()).isEqualTo(5);
        for (Long id : result) {
            assertThat(id).isIn(myScoreList);
        }
    }

    @Test
    void 별점_수정() {
        // given
        testData.createMember();
        String memberID = TestData.MEMBER_EMAIL;
        Long recipeID = 1L;
        double score = 3.5;
        Long scoreID = assessMyRecipeScoreUseCase.assessRecipeScore(memberID, recipeID, score);
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