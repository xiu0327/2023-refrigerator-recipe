package refrigerator.back.myscore;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScoreMappingDTO;
import refrigerator.back.myscore.adapter.out.mapper.MyRecipeScoreMapper;
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
        Double score = 4.5;
        MyRecipeScoreDomain domain = MyRecipeScoreDomain.builder()
                                        .memberID(memberID)
                                        .recipeID(recipeID)
                                        .score(score)
                                        .build();
        MyRecipeScore entity = myRecipeScoreMapper.toEntity(domain);
        assertNotNull(entity.getMemberID());
        assertNotNull(entity.getRecipeID());
        assertNotNull(entity.getScore());
    }

    @Test
    void dto_To_도메인(){
        MyRecipeScoreMappingDTO dto = MyRecipeScoreMappingDTO.builder()
                .scoreID(1L)
                .recipeName("레시피명")
                .recipeImage("레시피 이미지")
                .score(5.0)
                .build();
        MyRecipeScoreDomain domain = myRecipeScoreMapper.dtoToDomain(dto);
        // 도메인 모든 값이 not null
        assertNotNull(domain.getScoreID());
        assertNotNull(domain.getRecipeName());
        assertNotNull(domain.getRecipeImage());
        assertNotNull(domain.getScore());
    }
}
