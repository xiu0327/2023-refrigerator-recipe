package refrigerator.back.ingredient.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BusinessException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class IngredientTest {

    @Test
    @DisplayName("식재료 도메인 테스트")
    void ingredientTest() {

        Ingredient domain = Ingredient.builder()
                .id(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023,1,1))
                .registrationDate(LocalDate.of(2023,1,1))
                .email("email123@gmail.com")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .build();
        
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("감자");
        assertThat(domain.getExpirationDate()).isEqualTo(LocalDate.of(2023,1,1));
        assertThat(domain.getRegistrationDate()).isEqualTo(LocalDate.of(2023,1,1));
        assertThat(domain.getStorageMethod()).isEqualTo(IngredientStorageType.FREEZER);
        assertThat(domain.getEmail()).isEqualTo("email123@gmail.com");
        assertThat(domain.getCapacityUnit()).isEqualTo("g");
        assertThat(domain.getCapacity()).isEqualTo(30.0);
        assertThat(domain.getImage()).isEqualTo(1);
        assertThat(domain.isDeleted()).isFalse();

        domain.delete();
        assertThat(domain.isDeleted()).isTrue();

        domain.undelete();
        assertThat(domain.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("식재료 도메인 생성 테스트")
    void ingredientCreateTest() {
        Ingredient domain = Ingredient.create("감자",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 1),
                30.0,
                "g",
                IngredientStorageType.FREEZER,
                1,
                "email123@gmail.com");

        assertThat(domain.getId()).isNull();
        assertThat(domain.getName()).isEqualTo("감자");
        assertThat(domain.getExpirationDate()).isEqualTo(LocalDate.of(2023,1,1));
        assertThat(domain.getRegistrationDate()).isEqualTo(LocalDate.of(2023,1,1));
        assertThat(domain.getStorageMethod()).isEqualTo(IngredientStorageType.FREEZER);
        assertThat(domain.getEmail()).isEqualTo("email123@gmail.com");
        assertThat(domain.getCapacityUnit()).isEqualTo("g");
        assertThat(domain.getCapacity()).isEqualTo(30.0);
        assertThat(domain.getImage()).isEqualTo(1);
        assertThat(domain.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("식재료 도메인 수정 테스트")
    void ingredientModifyTest() {

        Ingredient domain = Ingredient.builder()
                .id(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023,1,1))
                .registrationDate(LocalDate.of(2023,1,1))
                .email("email123@gmail.com")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .build();

        domain.modify(LocalDate.of(2023, 1, 2), 60.0, IngredientStorageType.FRIDGE);
        
        assertThat(domain.getExpirationDate()).isEqualTo(LocalDate.of(2023,1,2));
        assertThat(domain.getStorageMethod()).isEqualTo(IngredientStorageType.FRIDGE);
        assertThat(domain.getCapacity()).isEqualTo(60.0);
    }
    
    @Test
    @DisplayName("식재료 용량 테스트")
    void ingredientCapacityTest() {
        assertThatThrownBy(() -> Ingredient.capacityCheck(10000.0)).isInstanceOf(BusinessException.class);
        assertThatThrownBy(() -> Ingredient.capacityCheck(0.0)).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("식재료 차감 테스트 1 : 차감하고자 하는 양이 더 적은 경우")
    void ingredientDeductionVolumeTest1() {

        Ingredient domain = Ingredient.builder()
                .id(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023,1,1))
                .registrationDate(LocalDate.of(2023,1,1))
                .email("email123@gmail.com")
                .capacity(60.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .build();
        
        domain.deductionVolume(30.0);
        
        assertThat(domain.getCapacity()).isEqualTo(30.0);
    }

    @Test
    @DisplayName("식재료 차감 테스트 2 : 차감하고자 하는 양이 같거나 더 많은 경우")
    void ingredientDeductionVolumeTest2() {

        Ingredient domain = Ingredient.builder()
                .id(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023,1,1))
                .registrationDate(LocalDate.of(2023,1,1))
                .email("email123@gmail.com")
                .capacity(60.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .build();

        domain.deductionVolume(60.0);

        assertThat(domain.getCapacity()).isEqualTo(0.0);

        domain.modify(LocalDate.of(2023,1,1), 60.0, IngredientStorageType.FREEZER);

        domain.deductionVolume(90.0);

        assertThat(domain.getCapacity()).isEqualTo(0.0);
    }
}