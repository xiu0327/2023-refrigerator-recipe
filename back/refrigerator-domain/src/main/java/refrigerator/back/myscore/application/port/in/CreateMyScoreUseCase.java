package refrigerator.back.myscore.application.port.in;


import refrigerator.back.myscore.application.dto.InCookingResponseDTO;

public interface CreateMyScoreUseCase {
    InCookingResponseDTO cooking(String memberID, Long recipeID, Double score);
}
