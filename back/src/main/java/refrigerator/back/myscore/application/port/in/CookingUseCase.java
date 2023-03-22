package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.adapter.in.dto.response.InCookingResponseDTO;

public interface CookingUseCase {
    InCookingResponseDTO cooking(String memberID, Long recipeID, Double score);
}
