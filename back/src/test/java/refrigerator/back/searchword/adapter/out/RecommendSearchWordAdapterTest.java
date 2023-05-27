package refrigerator.back.searchword.adapter.out;


import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.config.QuerydslConfig;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.searchword.application.port.out.FindIngredientsByMemberPort;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@Transactional
class RecommendSearchWordAdapterTest {

    @Autowired FindIngredientsByMemberPort findIngredientsByMemberPort;
    @Autowired EntityManager em;

    private final String email = "email123@gmail.com";
    private final String[] names = {"사과", "사과", "배", "소고기", "소고기"};
    private final String[] deletedNames = {"오이", "당근"};

    @Before
    void init(){
        for (int day = 1 ; day <= names.length ; day++){
            em.persist(Ingredient.create(
                    names[day - 1],
                    LocalDate.now().plusDays(day),
                    30.0,
                    "g",
                    IngredientStorageType.FREEZER,
                    day,
                    email));
        }
        for (int day = 1 ; day <= deletedNames.length ; day++){
            Ingredient ingredient = Ingredient.create(
                    deletedNames[day - 1],
                    LocalDate.now().plusDays(day),
                    30.0,
                    "g",
                    IngredientStorageType.FREEZER,
                    day,
                    email);
            ingredient.delete();
            em.persist(ingredient);
        }
    }

    @Test
    @DisplayName("식재료 이름 중복 제거 / 삭제된 식재료 제외 조건이 잘 작동하는지 확인")
    void getIngredients() {
        List<String> result = findIngredientsByMemberPort.getIngredients(email);
        // 1. 삭제된 식재료는 result 에 포함되지 않음
        Arrays.stream(deletedNames).forEach(
                name -> assertThat(name).isNotIn(result));
        // 2. 중복된 이름 X
        List<String> distinctNames = Arrays.stream(names).collect(Collectors.toList());
        assertThat(distinctNames.size()).isEqualTo(result.size());
    }
}