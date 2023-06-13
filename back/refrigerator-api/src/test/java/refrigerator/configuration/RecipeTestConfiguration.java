package refrigerator.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import refrigerator.back.recipe.adapter.out.QueryMyIngredientDataAdapter;
import refrigerator.back.recipe.adapter.out.QueryRecipeBasicDataAdapter;
import refrigerator.back.recipe.adapter.out.QueryRecipeIngredientAndCourseAdapter;
import refrigerator.back.recipe.adapter.out.mapper.OutRecipeBasicDataMapper;
import refrigerator.back.recipe.adapter.out.mapper.OutRecipeBasicDataMapperImpl;
import refrigerator.back.recipe.adapter.out.repository.RecipeSelectQueryRepository;
import refrigerator.back.recipe.application.port.out.GetMyIngredientDataPort;
import refrigerator.back.recipe.application.port.out.GetRecipeBasicsDataPort;
import refrigerator.back.recipe.application.port.out.GetRecipeIngredientAndCourseDataPort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class RecipeTestConfiguration {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public GetRecipeBasicsDataPort getRecipeBasicsDataPort(){
        return new QueryRecipeBasicDataAdapter(recipeSelectQueryRepository(), outRecipeBasicDataMapper());
    }

    @Bean
    public OutRecipeBasicDataMapper outRecipeBasicDataMapper(){
        return new OutRecipeBasicDataMapperImpl();
    }

    @Bean
    public GetMyIngredientDataPort getMyIngredientDataPort(){
        return new QueryMyIngredientDataAdapter(recipeSelectQueryRepository());
    }

    @Bean
    public GetRecipeIngredientAndCourseDataPort getRecipeIngredientAndCourseDataPort(){
        return new QueryRecipeIngredientAndCourseAdapter(recipeSelectQueryRepository());
    }

    @Bean
    public RecipeSelectQueryRepository recipeSelectQueryRepository() {
        return new RecipeSelectQueryRepository(jpaQueryFactory());
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(em);
    }
}
