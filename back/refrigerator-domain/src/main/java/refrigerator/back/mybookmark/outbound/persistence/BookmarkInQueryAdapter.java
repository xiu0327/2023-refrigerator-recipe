//package refrigerator.back.mybookmark.outbound.persistence;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Component;
//import refrigerator.back.mybookmark.application.dto.BookmarkDto;
//import refrigerator.back.mybookmark.application.dto.BookmarkPreviewDto;
//import refrigerator.back.mybookmark.application.dto.InBookmarkPreviewListDTO;
//import refrigerator.back.mybookmark.outbound.dto.OutBookmarkPreviewDto;
//import refrigerator.back.mybookmark.outbound.mapper.BookmarkDtoMapper;
//import refrigerator.back.mybookmark.outbound.repository.jpa.BookmarkRepository;
//import refrigerator.back.mybookmark.application.port.out.FindBookmarkListPort;
//import refrigerator.back.mybookmark.application.port.out.FindBookmarkPreviewListPort;
//import refrigerator.back.mybookmark.application.port.out.FindRecipeIdAddedBookmarkPort;
//
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class BookmarkInQueryAdapter implements FindBookmarkListPort, FindBookmarkPreviewListPort, FindRecipeIdAddedBookmarkPort {
//
//    private final BookmarkRepository repository;
//    private final BookmarkDtoMapper mapper;
//
//    @Override
//    public InBookmarkPreviewListDTO findBookmarkPreviewList(String memberId, int page, int size) {
//        Page<OutBookmarkPreviewDto> result = repository.findBookmarkPreview(memberId, PageRequest.of(page, size));
//        List<BookmarkPreviewDto> bookmarks = result.getContent().stream()
//                .map(mapper::toBookmarkPreviewDTO)
//                .collect(Collectors.toList());
//        return InBookmarkPreviewListDTO.builder()
//                .bookmarks(bookmarks)
//                .count(Long.valueOf(result.getTotalElements()).intValue()).build();
//
//    }
//
//    @Override
//    public List<BookmarkDto> findBookmarkList(String memberId, int page, int size) {
//        return repository.findBookmarkList(memberId, PageRequest.of(page, size))
//                .stream().map(mapper::toBookmarkDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Long> findRecipeIdByAddedBookmark(String memberId) {
//        return repository.findRecipeIdAddedBookmarks(memberId);
//    }
//}
