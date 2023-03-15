package refrigerator.back.myscore;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.myscore.adapter.out.MyRecipeScoreMapper;
import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class MyRecipeScoreMapperTest {

    @Autowired MyRecipeScoreMapper myRecipeScoreMapper;

    @Test
    void 도메인_To_엔티티(){
        String memberID = "email@naver.com";
        Long recipeID = 1L;
        MyRecipeScoreDomain domain = MyRecipeScoreDomain.builder()
                .scoreID(1L)
                .score(3.4)
                .registrationDate(LocalDateTime.now())
                .recipeImage("이미지")
                .recipeName("레시피명").build();
        MyRecipeScore entity = myRecipeScoreMapper.toEntity(domain, memberID, recipeID);
        // 엔티티 모든 값이 not null, 레시피 관련 정보는 테스트 대상 아님
        assertNotNull(entity.getScoreID());
        assertNotNull(entity.getMemberID());
        assertNotNull(entity.getRecipeID());
        assertNotNull(entity.getScore());
        assertNotNull(entity.getRegistrationDate());
    }

    @Test
    void 엔티티_To_도메인(){
        String memberID = "email@naver.com";
        Long recipeID = 1L;
        MyRecipeScore entity = MyRecipeScore.builder()
                .scoreID(1L)
                .memberID(memberID)
                .recipeID(recipeID)
                .score(3.4)
                .registrationDate(LocalDateTime.now())
                .recipeName("레시피 명")
                .recipeImage("레시피 이미지").build();
        MyRecipeScoreDomain domain = myRecipeScoreMapper.toDomain(entity);
        // 도메인 모든 값이 not null
        assertNotNull(domain.getScoreID());
        assertNotNull(domain.getRecipeName());
        assertNotNull(domain.getRecipeImage());
        assertNotNull(domain.getScore());
        assertNotNull(domain.getRegistrationDate());
    }
}
