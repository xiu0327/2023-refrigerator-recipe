package refrigerator.back.ingredient.application.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import refrigerator.back.global.common.BaseTimeEntity;
import refrigerator.back.global.common.BaseTimeEntityWithModify;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "ingredient")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "capacity", nullable = false, length = 30)
    private Double capacity;

    @Column(name = "capacity_unit", nullable = false, length = 30)
    private String capacityUnit;

    @Column(name = "storage_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private IngredientStorageType storageMethod;

    @Column(name = "image", nullable = false)
    private Integer image;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "email", nullable = false)
    private String email;

    public void delete(){
        this.deleted = true;
    }

    public void undelete() {
        this.deleted = false;
    }

    public Long getRemainDays() {
        return ChronoUnit.DAYS.between(this.expirationDate, LocalDate.now());
    }

    public Ingredient(String name, LocalDate expirationDate, Double capacity, String capacityUnit, IngredientStorageType storageMethod, Integer imageId, String email) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        this.capacityUnit = capacityUnit;
        this.storageMethod = storageMethod;
        this.registrationDate = LocalDate.now();
        this.image = imageId;
        this.email = email;
    }

    public static Ingredient create(String name, LocalDate expirationDate, Double capacity, String capacityUnit, IngredientStorageType storageMethod, Integer imageId, String email) {
        capacityCheck(capacity);
        Ingredient ingredient = new Ingredient(name, expirationDate, capacity, capacityUnit, storageMethod, imageId, email);
        ingredient.undelete();
        return ingredient;
    }

    public void modify(LocalDate expirationDate, Double capacity, IngredientStorageType storageMethod) {
        capacityCheck(capacity);
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        this.storageMethod = storageMethod;
    }

    private static void capacityCheck(Double capacity) {
        if(capacity > 9999.9) {
            throw new BusinessException(IngredientExceptionType.EXCEEDED_CAPACITY_RANGE);
        }
    }

    public void deductionVolume(Double volume) {
        capacityCheck(volume);

        if (capacity < volume){
            this.capacity = 0.0;
            return;
        }
        this.capacity -= volume;
    }
}
