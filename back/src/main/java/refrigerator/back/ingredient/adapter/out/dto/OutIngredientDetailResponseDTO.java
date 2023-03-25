package refrigerator.back.ingredient.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OutIngredientDetailResponseDTO {

    private Long id;
    private String name;
    private LocalDateTime registrationDate;
    private String storageMethod;
    private LocalDateTime expirationDate;
    private String capacity;
    private String capacityUnit;
    private String email;

    @QueryProjection
    public OutIngredientDetailResponseDTO(Long id, String name, LocalDateTime registrationDate, String storageMethod, LocalDateTime expirationDate, String capacity, String capacityUnit, String email) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.storageMethod = storageMethod;
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        this.capacityUnit = capacityUnit;
        this.email = email;
    }
}
