package refrigerator.back.global.time;

import lombok.RequiredArgsConstructor;
import refrigerator.back.recipe_searchword.application.domain.Ingredient;

import java.time.LocalDate;

@RequiredArgsConstructor
public class TestDate implements Date{

    private final LocalDate currentDate;

    @Override
    public LocalDate now() {
        return this.currentDate;
    }
}
