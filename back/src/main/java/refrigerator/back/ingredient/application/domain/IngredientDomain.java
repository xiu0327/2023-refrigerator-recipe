package refrigerator.back.ingredient.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDomain {

    private Long ingredientId;
    private String name;
    private LocalDateTime expirationDate;
    private String capacity;
    private CapacityUnit capacityUnit;
    private StorageMethod storageMethod;
    private LocalDateTime registrationDate;
    private String email;

    /* 비즈니스 로직 */

    public IngredientDomain(String name, LocalDateTime expirationDate, String capacity, String capacityUnit, String storageMethod, String email) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        // 용량 설정
        // 보관 방법 설정 => 둘은 enum 타입
        this.email = email;
    }


    public static IngredientDomain register(String name, LocalDateTime expirationDate,
                                            String capacity, String capacityUnit, String storageMethod, String email) {
        return new IngredientDomain(name, expirationDate, capacity, capacityUnit, storageMethod, email);
    }

    public void modify(LocalDateTime expirationDate, String capacity, String storageMethod) {
        this.expirationDate = expirationDate;
        this.capacity = capacity;
//        this.storageMethod
    }
}
