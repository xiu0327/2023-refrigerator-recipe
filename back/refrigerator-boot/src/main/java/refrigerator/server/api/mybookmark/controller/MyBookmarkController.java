package refrigerator.server.api.mybookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.server.api.mybookmark.dto.BookmarkIdResponseDTO;
import refrigerator.back.mybookmark.application.dto.InBookmarkDTO;
import refrigerator.server.api.mybookmark.dto.InBookmarkListDTO;
import refrigerator.back.mybookmark.application.dto.InBookmarkPreviewListDTO;
import refrigerator.back.mybookmark.application.port.in.*;


import java.util.List;


@RestController
@RequiredArgsConstructor
public class MyBookmarkController {

    private final AddBookmarkUseCase addBookmarkUseCase;
    private final RemoveBookmarkUseCase removeBookmarkUseCase;
    private final FindRecipeIdByAddedBookmarkUseCase findRecipeIdByAddedBookmarkUseCase;
    private final FindBookmarkListUseCase findBookmarkListUseCase;
    private final FindBookmarkPreviewUseCase findBookmarkPreviewUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/my-bookmark/add/{recipeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BookmarkIdResponseDTO addBookmark(@PathVariable("recipeId") Long recipeId){
        Long bookmarkId = addBookmarkUseCase.add(memberInformation.getMemberEmail(), recipeId);
        return new BookmarkIdResponseDTO(bookmarkId);
    }

    @DeleteMapping("/api/my-bookmark/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookmark(@RequestParam("bookmarkId") Long bookmarkId){
        removeBookmarkUseCase.remove(bookmarkId);
    }

    @DeleteMapping("/api/my-bookmark/remove/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookmarkByRecipeId(@PathVariable("recipeId") Long recipeId){
        removeBookmarkUseCase.removeByRecipeId(recipeId, memberInformation.getMemberEmail());
    }

    @GetMapping("/api/my-bookmark/added")
    public BasicListResponseDTO<Long> findRecipeIdList(){
        List<Long> result = findRecipeIdByAddedBookmarkUseCase.findRecipeIdList(memberInformation.getMemberEmail());
        return new BasicListResponseDTO<>(result);
    }

    @GetMapping("/api/my-bookmark/list")
    public InBookmarkListDTO findMyBookmarkList(@RequestParam("page") int page,
                                                @RequestParam(name = "size", defaultValue = "11") int size){
        List<InBookmarkDTO> bookmarks = findBookmarkListUseCase.findBookmarks(memberInformation.getMemberEmail(), page, size);
        return InBookmarkListDTO.builder().bookmarks(bookmarks).build();
    }

    @GetMapping("/api/my-bookmark/preview")
    public InBookmarkPreviewListDTO findMyBookmarkPreviewList(@RequestParam(name = "size", defaultValue = "5") int size){
        return findBookmarkPreviewUseCase.findPreviews(memberInformation.getMemberEmail(), size);
    }

}
