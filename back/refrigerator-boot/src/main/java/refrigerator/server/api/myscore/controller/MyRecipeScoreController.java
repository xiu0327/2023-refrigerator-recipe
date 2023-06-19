package refrigerator.server.api.myscore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.myscore.application.dto.InCookingResponseDTO;
import refrigerator.back.myscore.application.dto.InMyScoreDTO;
import refrigerator.back.myscore.application.dto.InMyScoreListDTO;
import refrigerator.back.myscore.application.dto.InMyScorePreviewDTO;
import refrigerator.back.myscore.application.port.in.CreateMyScoreUseCase;
import refrigerator.back.myscore.application.port.in.FindMyScoreListUseCase;
import refrigerator.back.myscore.application.port.in.FindMyScorePreviewUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyScoreUseCase;


@RestController
@RequiredArgsConstructor
public class MyRecipeScoreController {

    private final FindMyScoreListUseCase findMyScoreListUseCase;
    private final ModifyMyScoreUseCase modifyMyScoreUseCase;
    private final FindMyScorePreviewUseCase findMyScorePreviewUseCase;
    private final CreateMyScoreUseCase cookingUseCase;
    private final GetMemberEmailUseCase memberInformation;


    @PostMapping("/api/my-score/cooking")
    public ResponseEntity<InCookingResponseDTO> cooking(@RequestParam("recipeId") Long recipeID,
                                                        @RequestParam("score") Double score){
        InCookingResponseDTO cooking = cookingUseCase.cooking(memberInformation.getMemberEmail(), recipeID, score);
        if (cooking.getIsCreated()){
            return new ResponseEntity<>(cooking, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(cooking, HttpStatus.OK);
    }

    @GetMapping("/api/my-score/list")
    public InMyScoreListDTO<InMyScoreDTO> findMyScore(@RequestParam("page") int page,
                                                      @RequestParam(value = "size", defaultValue = "11") int size){
        return findMyScoreListUseCase.findMyScoreList(memberInformation.getMemberEmail(), page, size);
    }

    @GetMapping("/api/my-score/preview")
    public InMyScoreListDTO<InMyScorePreviewDTO> preview(@RequestParam(value = "size", defaultValue = "5") int size){
        return findMyScorePreviewUseCase.findPreviewList(memberInformation.getMemberEmail(), size);
    }

    @PutMapping("/api/my-score")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modify(@RequestParam("scoreId") Long scoreID,
                       @RequestParam("score") Double score){
        modifyMyScoreUseCase.modify(scoreID, score);
    }
}
