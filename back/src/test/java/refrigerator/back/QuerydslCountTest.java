package refrigerator.back;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.application.domain.entity.QRecipe;
import refrigerator.back.recipe.application.domain.entity.Recipe;

import java.util.List;

import static refrigerator.back.recipe.application.domain.entity.QRecipe.*;

@SpringBootTest
@Transactional
@Slf4j
public class QuerydslCountTest {

    @Autowired JPAQueryFactory jpaQueryFactory;

    @Test
    void 테스트(){
        Pageable page = PageRequest.of(0, 11);
        List<Recipe> content = jpaQueryFactory
                .select(recipe)
                .from(recipe)
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(recipe.count())
                .from(recipe);

        Page<Recipe> result = PageableExecutionUtils.getPage(content, page, count::fetchOne);
        log.info("total elements = {}", result.getTotalElements());
        log.info("total elements = {}", result.getNumberOfElements());
    }
}
