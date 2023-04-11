package refrigerator.back.word_completion.adapter.out;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.word_completion.application.port.out.FindRecipeNameListPort;

import java.util.List;

import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;

@Repository
@RequiredArgsConstructor
public class RecipeWordCompletionAdapter implements FindRecipeNameListPort {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findNameList() {
        return jpaQueryFactory
                .select(recipe.recipeName)
                .from(recipe)
                .fetch();
    }
}
