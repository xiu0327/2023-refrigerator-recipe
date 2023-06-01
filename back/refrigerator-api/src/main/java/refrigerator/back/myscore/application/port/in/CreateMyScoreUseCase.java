package refrigerator.back.myscore.application.port.in;


import refrigerator.back.myscore.adapter.in.dto.response.InCookingResponseDTO;

public interface CreateMyScoreUseCase {
    InCookingResponseDTO cooking(String memberID, Long recipeID, Double score);
}
