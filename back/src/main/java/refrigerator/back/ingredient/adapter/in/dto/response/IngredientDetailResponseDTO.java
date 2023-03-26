package refrigerator.back.ingredient.adapter.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDetailResponseDTO {

    private Long id;
    private String name;
    private LocalDateTime registrationDate;
    private String storageMethod;
    private LocalDateTime expirationDate;
    private String capacity;
    private String capacityUnit;
    private String email;
    // 이미지??
}
