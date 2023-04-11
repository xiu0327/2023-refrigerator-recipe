package refrigerator.back.word_completion.adapter.infra;

import org.springframework.stereotype.Component;
import refrigerator.back.word_completion.application.domain.RecipeNameParserUtils;


@Component
public class RecipeNameParserUtilsImpl implements RecipeNameParserUtils {
    @Override
    public String split(String keyword) {
        return JamoUtils.split(keyword);
    }
}
