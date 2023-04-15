package refrigerator.back.mybookmark.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkListDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewListDTO;
import refrigerator.back.mybookmark.adapter.mapper.BookmarkDtoMapper;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkPreviewDTO;
import refrigerator.back.mybookmark.adapter.out.repository.BookmarkRepository;
import refrigerator.back.mybookmark.application.domain.MyBookmark;
import refrigerator.back.mybookmark.application.port.out.BookmarkReadPort;
import refrigerator.back.mybookmark.application.port.out.BookmarkWritePort;
import refrigerator.back.mybookmark.exception.MyBookmarkExceptionType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookmarkPersistenceAdapter implements BookmarkReadPort, BookmarkWritePort {

    private final BookmarkRepository repository;
    private final BookmarkDtoMapper mapper;

    @Override
    public InBookmarkPreviewListDTO findBookmarkPreviewList(String memberId, int page, int size) {
        Page<OutBookmarkPreviewDTO> result =
                repository.findBookmarkPreview(memberId, PageRequest.of(page, size));
        List<InBookmarkPreviewDTO> bookmarks = result.getContent().stream()
                .map(mapper::toBookmarkPreviewDTO)
                .collect(Collectors.toList());
        return InBookmarkPreviewListDTO.builder()
                .bookmarks(bookmarks)
                .count(Long.valueOf(result.getTotalElements()).intValue()).build();

    }

    @Override
    public List<InBookmarkDTO> findBookmarkList(String memberId, int page, int size) {
        return repository.findBookmarkList(memberId, PageRequest.of(page, size))
                .stream().map(bookmark ->
                    mapper.toBookmarkDTO(
                            bookmark,
                            bookmark.getScore().calculateScore())
                )
                .collect(Collectors.toList());
    }

    @Override
    public Long save(MyBookmark bookmark) {
        repository.save(bookmark);
        return bookmark.getBookmarkId();
    }

    @Override
    public Optional<MyBookmark> findBookmarkById(Long bookmarkId) {
        return repository.findByBookmarkId(bookmarkId);
    }

    @Override
    public Optional<MyBookmark> findBookmarkByMemberIdAndRecipeId(String memberId, Long recipeId) {
        return repository.findByMemberIdAndRecipeId(memberId, recipeId);
    }

    @Override
    public List<Long> findRecipeIdByAddedBookmark(String memberId) {
        return repository.findRecipeIdAddedBookmarks(memberId);
    }

}
