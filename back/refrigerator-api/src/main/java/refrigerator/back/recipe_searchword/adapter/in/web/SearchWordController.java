package refrigerator.back.recipe_searchword.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.recipe_searchword.application.port.in.DeleteSearchWordUseCase;
import refrigerator.back.recipe_searchword.application.port.in.FindLastSearchWordUseCase;
import refrigerator.back.recipe_searchword.application.port.in.FindRecommendSearchWordUseCase;


@RestController
@RequiredArgsConstructor
public class SearchWordController {

    private final DeleteSearchWordUseCase deleteSearchWordUseCase;
    private final FindLastSearchWordUseCase findLastSearchWordUseCase;
    private final FindRecommendSearchWordUseCase findRecommendSearchWordUseCase;
    private final GetMemberEmailUseCase memberInformation;


    @GetMapping("/api/search-word/recommend")
    public BasicListResponseDTO<String> getRecommendSearchWords(){
        String memberId = memberInformation.getMemberEmail();
        return new BasicListResponseDTO<>(
                findRecommendSearchWordUseCase.getRecommendSearchWords(memberId));
    }

    @GetMapping("/api/search-word/last")
    public BasicListResponseDTO<String> getLastSearchWords(){
        String memberId = memberInformation.getMemberEmail();
        return new BasicListResponseDTO<>(
                findLastSearchWordUseCase.getLastSearchWords(memberId)
        );
    }

    @DeleteMapping("/api/search-word")
    public void deleteSearchWord(@RequestParam("word") String word){
        deleteSearchWordUseCase.delete(memberInformation.getMemberEmail(), word);
    }

}
