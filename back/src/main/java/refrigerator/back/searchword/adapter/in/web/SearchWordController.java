package refrigerator.back.searchword.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.global.common.MemberInformation;
import refrigerator.back.searchword.application.port.in.DeleteSearchWordUseCase;
import refrigerator.back.searchword.application.port.in.FindLastSearchWordUseCase;
import refrigerator.back.searchword.application.port.in.FindRecommendSearchWordUseCase;

@RestController
@RequiredArgsConstructor
public class SearchWordController {

    private final DeleteSearchWordUseCase deleteSearchWordUseCase;
    private final FindLastSearchWordUseCase findLastSearchWordUseCase;
    private final FindRecommendSearchWordUseCase findRecommendSearchWordUseCase;

    @GetMapping("/api/search-word/recommend")
    public BasicListResponseDTO<String> getRecommendSearchWords(){
        String memberId = MemberInformation.getMemberEmail();
        return new BasicListResponseDTO<>(
                findRecommendSearchWordUseCase.getRecommendSearchWords(memberId));
    }

    @GetMapping("/api/search-word/last")
    public BasicListResponseDTO<String> getLastSearchWords(){
        String memberId = MemberInformation.getMemberEmail();
        return new BasicListResponseDTO<>(
                findLastSearchWordUseCase.getLastSearchWords(memberId)
        );
    }

    @DeleteMapping("/api/search-word")
    public void deleteSearchWord(@RequestParam("word") String word){
        deleteSearchWordUseCase.delete(MemberInformation.getMemberEmail(), word);
    }

}
