package refrigerator.back.myscore.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.myscore.adapter.in.dto.CookingResponseDTO;
import refrigerator.back.myscore.adapter.in.dto.ScoreListResponseDTO;
import refrigerator.back.myscore.adapter.in.mapper.MyRecipeScoreInputMapper;
import refrigerator.back.myscore.application.domain.MyRecipeScoreListDomain;
import refrigerator.back.myscore.application.port.in.CookingUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScoreListUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScorePreviewUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyRecipeScoreUseCase;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MyRecipeScoreController {

    private final FindMyRecipeScoreListUseCase findMyRecipeScoreListUseCase;
    private final ModifyMyRecipeScoreUseCase modifyMyRecipeScoreUseCase;
    private final FindMyRecipeScorePreviewUseCase findMyRecipeScorePreviewUseCase;
    private final CookingUseCase cookingUseCase;
    private final MyRecipeScoreInputMapper mapper;

    @PostMapping("/api/my-score/cooking")
    public ResponseEntity<CookingResponseDTO> cooking(@RequestParam("recipeID") Long recipeID,
                                                      @RequestParam("score") Double score){
        /* 추후 스프링 시큐리티로 회원 이메일 가져올 예정 */
        String memberID = "";
        CookingResponseDTO cooking = cookingUseCase.cooking(memberID, recipeID, score);
        if (cooking.getIsCreated()){
            return new ResponseEntity<>(cooking, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(cooking, HttpStatus.OK);
    }

    @GetMapping("/api/my-score")
    public ScoreListResponseDTO findMyScore(@RequestParam("page") int page,
                                            @RequestParam("size") int size){
        /* 추후 스프링 시큐리티로 회원 이메일 가져올 예정 */
        String memberID = "";
        MyRecipeScoreListDomain myScoreList = findMyRecipeScoreListUseCase.findMyScoreList(memberID, page, size);
        return ScoreListResponseDTO.builder()
                .scores(myScoreList.getScores().stream()
                        .map(mapper::toScoreDto)
                        .collect(Collectors.toList())).build();
    }

    @GetMapping("/api/my-score/preview")
    public ScoreListResponseDTO preview(@RequestParam(value = "size", defaultValue = "5") int size){
        /* 추후 스프링 시큐리티로 회원 이메일 가져올 예정 */
        String memberID = "";
        MyRecipeScoreListDomain myScoreList = findMyRecipeScorePreviewUseCase.findPreview(memberID, size);
        return ScoreListResponseDTO.builder()
                .scores(myScoreList.getScores().stream()
                        .map(mapper::toScoreDto)
                        .collect(Collectors.toList()))
                .count(myScoreList.getCount()).build();
    }

    @PutMapping("/api/my-score")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modify(@RequestParam("scoreID") Long scoreID,
                                                @RequestParam("score") Double score){
        modifyMyRecipeScoreUseCase.modifyMyRecipeScore(scoreID, score);
    }
}
