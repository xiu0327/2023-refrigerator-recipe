package refrigerator.back.ingredient.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public class OutIngredientResponseDTO {

    private Long id;
    private String name;
    private LocalDateTime registrationDate;
    private LocalDateTime expirationDate;

    @QueryProjection
    public OutIngredientResponseDTO(Long id, String name, LocalDateTime registrationDate, LocalDateTime expirationDate) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
    }
}
