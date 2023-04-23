package back.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class OutIngredientDTO {

    private String email;
    private String name;
    private Long count;

}
