package refrigerator.back.myscore.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.myscore.adapter.in.dto.MyRecipeScoreBasicResponseDTO;
import refrigerator.back.myscore.adapter.in.dto.MyRecipeScoreListResponseDTO;
import refrigerator.back.myscore.adapter.in.mapper.MyRecipeScoreInputMapper;
import refrigerator.back.myscore.application.domain.MyRecipeScoreListDomain;
import refrigerator.back.myscore.application.port.in.AssessMyRecipeScoreUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScoreListUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScorePreviewUseCase;
import refrigerator.back.myscore.application.port.in.ModifyMyRecipeScoreUseCase;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MyRecipeScoreController {

    private final AssessMyRecipeScoreUseCase assessMyRecipeScoreUseCase;
    private final FindMyRecipeScoreListUseCase findMyRecipeScoreListUseCase;
    private final ModifyMyRecipeScoreUseCase modifyMyRecipeScoreUseCase;
    private final FindMyRecipeScorePreviewUseCase findMyRecipeScorePreviewUseCase;
    private final MyRecipeScoreInputMapper mapper;

    @PostMapping("/api/my-score")
    @ResponseStatus(HttpStatus.CREATED)
    public MyRecipeScoreBasicResponseDTO assess(@RequestParam("recipeID") Long recipeID,
                                                @RequestParam("score") Double score){
        /* 추후 스프링 시큐리티로 회원 이메일 가져올 예정 */
        String memberID = "";
        Long scoreID = assessMyRecipeScoreUseCase.assessRecipeScore(memberID, recipeID, score);
        return new MyRecipeScoreBasicResponseDTO(scoreID);
    }

    @GetMapping("/api/my-score")
    public MyRecipeScoreListResponseDTO findMyScore(@RequestParam("page") int page,
                                                    @RequestParam("size") int size){
        /* 추후 스프링 시큐리티로 회원 이메일 가져올 예정 */
        String memberID = "";
        MyRecipeScoreListDomain myScoreList = findMyRecipeScoreListUseCase.findMyScoreList(memberID, page, size);
        return MyRecipeScoreListResponseDTO.builder()
                .scores(myScoreList.getScores().stream()
                        .map(mapper::toScoreDto)
                        .collect(Collectors.toList())).build();
    }

    @GetMapping("/api/my-score/preview")
    public MyRecipeScoreListResponseDTO preview(@RequestParam(value = "size", defaultValue = "5") int size){
        /* 추후 스프링 시큐리티로 회원 이메일 가져올 예정 */
        String memberID = "";
        MyRecipeScoreListDomain myScoreList = findMyRecipeScorePreviewUseCase.findPreview(memberID, size);
        return MyRecipeScoreListResponseDTO.builder()
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
