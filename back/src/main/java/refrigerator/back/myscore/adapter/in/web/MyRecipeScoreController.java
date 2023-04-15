package refrigerator.back.myscore.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.MemberInformation;
import refrigerator.back.myscore.adapter.in.dto.response.InCookingResponseDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreListDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScorePreviewDTO;
import refrigerator.back.myscore.application.port.in.CookingUseCase;
import refrigerator.back.myscore.application.port.in.FindMyScoreListUseCase;
import refrigerator.back.myscore.application.port.in.FindMyScorePreviewUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyScoreUseCase;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class MyRecipeScoreController {

    private final FindMyScoreListUseCase findMyScoreListUseCase;
    private final ModifyMyScoreUseCase modifyMyScoreUseCase;
    private final FindMyScorePreviewUseCase findMyScorePreviewUseCase;
    private final CookingUseCase cookingUseCase;


    @PostMapping("/api/my-score/cooking")
    public ResponseEntity<InCookingResponseDTO> cooking(@RequestParam("recipeId") Long recipeID,
                                                        @RequestParam("score") Double score){
        InCookingResponseDTO cooking = cookingUseCase.cooking(getMemberEmail(), recipeID, score);
        if (cooking.getIsCreated()){
            return new ResponseEntity<>(cooking, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(cooking, HttpStatus.OK);
    }

    @GetMapping("/api/my-score")
    public InMyScoreListDTO<InMyScoreDTO> findMyScore(@RequestParam("page") int page,
                                            @RequestParam("size") int size){
        return findMyScoreListUseCase.findMyScoreList(getMemberEmail(), page, size);
    }

    @GetMapping("/api/my-score/preview")
    public InMyScoreListDTO<InMyScorePreviewDTO> preview(@RequestParam(value = "size", defaultValue = "5") int size){
        return findMyScorePreviewUseCase.findPreviewList(getMemberEmail(), size);
    }

    @PutMapping("/api/my-score")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modify(@RequestParam("scoreId") Long scoreID,
                       @RequestParam("score") Double score){
        modifyMyScoreUseCase.modify(scoreID, score);
    }
}
