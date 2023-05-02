package refrigerator.back.word_completion.adapter.out;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.word_completion.application.port.out.FindRecipeWordListPort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static refrigerator.back.ingredient.application.domain.QRegisteredIngredient.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;

@Repository
@RequiredArgsConstructor
public class RecipeWordCompletionAdapter implements FindRecipeWordListPort {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findRecipeNameList() {
        return jpaQueryFactory
                .select(recipe.recipeName)
                .from(recipe)
                .fetch();
    }

}
