package refrigerator.back.ingredient.adapter.in.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRegisterRequestDTO {

    @NotEmpty
    private String name;                    // 식재료명

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate expirationDate;       // 유통기한

    @NotEmpty
    private Double capacity;               // 용량

    @NotEmpty
    private String capacityUnit;            // 용량 단위

    @NotEmpty
    private String storageMethod;           // 보관 방식

    @NotEmpty
    private Integer imageId;                // 사진 (수정 가능성)
}


