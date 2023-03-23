package refrigerator.back.ingredient.adapter.out.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingredient")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long ingredientId;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "capacity", nullable = false, length = 30)
    private String capacity;

    @Column(name = "capacity_unit", nullable = false, length = 30)
    private String capacityUnit;

    @Column(name = "storage_method", nullable = false, length = 30)
    private String storageMethod;

    //
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "email", nullable = false)
    private String email;

}
