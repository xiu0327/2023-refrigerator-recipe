package refrigerator.back.ingredient.adapter.out.mapper;

import refrigerator.back.ingredient.adapter.out.entity.IngredientEntity;
import refrigerator.back.ingredient.application.domain.IngredientDomain;

public class IngredientMapperImpl implements IngredientMapper{

    @Override
    public IngredientDomain toIngredientDomain(IngredientEntity entity) {
        return IngredientDomain.builder()
                .ingredientId(entity.getIngredientId())
                .name(entity.getName())
                .expirationDate(entity.getExpirationDate())
                .capacity(entity.getCapacity())
//                .capacityUnit(entity.getCapacityUnit())
//                .storageMethod(entity.getStorageMethod())
                .registrationDate(entity.getRegistrationDate())
                .email(entity.getEmail())
                .build();
    }

    @Override
    public IngredientEntity toIngredientEntity(IngredientDomain domain) {
        return IngredientEntity.builder()
                .ingredientId(domain.getIngredientId())
                .name(domain.getName())
                .expirationDate(domain.getExpirationDate())
                .capacity(domain.getCapacity())
//                .capacityUnit(domain.getCapacityUnit())
//                .storageMethod(domain.getStorageMethod())
                .registrationDate(domain.getRegistrationDate())
                .email(domain.getEmail())
                .build();
    }
}
