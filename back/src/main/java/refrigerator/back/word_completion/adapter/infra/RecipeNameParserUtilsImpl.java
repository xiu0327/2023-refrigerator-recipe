package refrigerator.back.word_completion.adapter.infra;

import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.word_completion.adapter.infra.hangule.HangulParser;
import refrigerator.back.word_completion.adapter.infra.hangule.HangulParserException;
import refrigerator.back.word_completion.adapter.infra.hangule.HangulParserExceptionType;
import refrigerator.back.word_completion.adapter.infra.hangule.JamoUtils;
import refrigerator.back.word_completion.application.domain.RecipeNameParserUtils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class RecipeNameParserUtilsImpl implements RecipeNameParserUtils {
    @Override
    public String split(String keyword) {
        return JamoUtils.split(keyword);
    }

    @Override
    public String join(String keyword) {
        return Normalizer.normalize(keyword, Normalizer.Form.NFC);
    }
}
