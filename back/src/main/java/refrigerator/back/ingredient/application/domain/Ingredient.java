package refrigerator.back.ingredient.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import refrigerator.back.global.common.BaseTimeEntityWithModify;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingredient")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "capacity", nullable = false, length = 30)
    private String capacity;

    @Column(name = "capacity_unit", nullable = false, length = 30)
    private String capacityUnit;

    @CreatedDate
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "storage_method", nullable = false, length = 30)
    private String storageMethod;

    @Column(name = "email", nullable = false)
    private String email;

    public static Ingredient create(String name, LocalDateTime expirationDate, String capacity, String capacityUnit, String storageMethod, String email) {
        return Ingredient.builder()
                .name(name)
                .expirationDate(expirationDate)
                .capacity(capacity)
                .capacityUnit(capacityUnit)
                .registrationDate(LocalDateTime.now())
                .storageMethod(storageMethod)
                .email(email)
                .build();
    }

    public void modify(LocalDateTime expirationDate, String capacity, String storageMethod) {
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        this.storageMethod = storageMethod;
    }
}
