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
public class IngredientResponseDTO {

    private Long id;
    private String name;
    private LocalDateTime registrationDate;
    private LocalDateTime expirationDate;
    // 이미지?
}
