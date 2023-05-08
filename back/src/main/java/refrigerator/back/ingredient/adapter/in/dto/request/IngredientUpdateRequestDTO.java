package refrigerator.back.ingredient.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientUpdateRequestDTO {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;                       // 유통기한

    @NotNull
    @Positive
    private Double capacity;                                // 용량

    @NotNull
    private IngredientStorageType storageMethod;           // 보관방법
}
