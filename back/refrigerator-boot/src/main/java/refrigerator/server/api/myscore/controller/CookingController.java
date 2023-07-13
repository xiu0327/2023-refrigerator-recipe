package refrigerator.server.api.myscore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.myscore.application.port.in.CookingUseCase;

@RestController
@RequiredArgsConstructor
public class CookingController {

    private final CookingUseCase cookingUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @PostMapping("/api/recipe/{recipeId}/cooking/first")
    @ResponseStatus(HttpStatus.CREATED)
    public void first(@PathVariable("recipeId") Long recipeId, @RequestParam("score") Double score){
        String memberId = getMemberEmailUseCase.getMemberEmail();
        cookingUseCase.firstCooking(memberId, recipeId, score);
    }

    @PutMapping("/api/recipe/{recipeId}/cooking/retry")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retry(@PathVariable("recipeId") Long recipeId,
                      @RequestParam("newScore") Double newScore){
        String memberId = getMemberEmailUseCase.getMemberEmail();
        cookingUseCase.retryCooking(memberId, recipeId, newScore);
    }

}
