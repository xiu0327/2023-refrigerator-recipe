package refrigerator.back.ingredient.application.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientSearchCondition {

    String storage;
    boolean deadline;
    String email;
}
