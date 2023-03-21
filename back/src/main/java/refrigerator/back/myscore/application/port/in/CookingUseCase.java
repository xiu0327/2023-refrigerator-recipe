package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.adapter.in.dto.CookingResponseDTO;

public interface CookingUseCase {
    CookingResponseDTO cooking(String memberID, Long recipeID, Double score);
}
