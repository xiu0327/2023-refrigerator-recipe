package refrigerator.back.ingredient.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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
public class IngredientRegisterRequestDTO {

    @NotBlank
    private String name;                    // 식재료명

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;       // 유통기한

    @NotNull
    @Positive
    private Double capacity;                // 용량

    @NotNull
    private IngredientStorageType storageMethod;           // 보관 방식
}


