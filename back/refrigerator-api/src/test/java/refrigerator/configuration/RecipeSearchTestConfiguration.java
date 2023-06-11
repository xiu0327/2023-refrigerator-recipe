package refrigerator.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import refrigerator.back.recipe_search.adapter.out.RecipeSearchAdapter;
import refrigerator.back.recipe_search.adapter.out.RecipeSearchConditionAdapter;
import refrigerator.back.recipe_search.adapter.out.repository.RecipeSearchSelectQueryRepository;
import refrigerator.back.recipe_search.application.port.out.GetRecipeSearchDataPort;
import refrigerator.back.recipe_search.application.port.out.GetSearchConditionDataPort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class RecipeSearchTestConfiguration {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public GetSearchConditionDataPort getSearchConditionDataPort(){
        return new RecipeSearchConditionAdapter(recipeSearchSelectQueryRepository());
    }

    @Bean
    public GetRecipeSearchDataPort getRecipeSearchDataPort(){
        return new RecipeSearchAdapter(recipeSearchSelectQueryRepository());
    }

    @Bean
    public RecipeSearchSelectQueryRepository recipeSearchSelectQueryRepository(){
        return new RecipeSearchSelectQueryRepository(jpaQueryFactory());
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(em);
    }
}
