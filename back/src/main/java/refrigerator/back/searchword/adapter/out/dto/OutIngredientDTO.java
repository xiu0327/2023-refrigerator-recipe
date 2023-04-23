package refrigerator.back.searchword.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public class OutIngredientDTO {
    private String name;
    private LocalDate date;

    @QueryProjection
    public OutIngredientDTO(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}
