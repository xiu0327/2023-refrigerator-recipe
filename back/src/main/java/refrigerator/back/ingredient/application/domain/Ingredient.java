package refrigerator.back.ingredient.application.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import refrigerator.back.global.common.BaseTimeEntity;
import refrigerator.back.global.common.BaseTimeEntityWithModify;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private Integer capacity;

    @Column(name = "capacity_unit", nullable = false, length = 30)
    private String capacityUnit;

    @Column(name = "storage_method", nullable = false, length = 30)
    private String storageMethod;

    @Column(name = "ingredient_image_id", nullable = false)
    private Long ingredientImageId;

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

    public Ingredient(String name, LocalDate expirationDate, Integer capacity, String capacityUnit, String storageMethod, Long imageId, String email) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        this.capacityUnit = capacityUnit;
        this.storageMethod = storageMethod;
        this.registrationDate = LocalDate.now();
        this.ingredientImageId = imageId;
        this.email = email;
    }

    public static Ingredient create(String name, LocalDate expirationDate, Integer capacity, String capacityUnit, String storageMethod, Long imageId, String email) {
        Ingredient ingredient = new Ingredient(name, expirationDate, capacity, capacityUnit, storageMethod, imageId, email);
        ingredient.undelete();
        return ingredient;
    }

    public void modify(LocalDate expirationDate, Integer capacity, String storageMethod) {
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        this.storageMethod = storageMethod;
    }
}
