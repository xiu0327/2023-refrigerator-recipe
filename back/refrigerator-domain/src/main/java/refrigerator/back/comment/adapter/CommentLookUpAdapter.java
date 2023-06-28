//package refrigerator.back.comment.adapter.persistence;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Repository;
//import refrigerator.back.comment.adapter.mapper.OutCommentMapper;
//import refrigerator.back.comment.adapter.dto.OutCommentDTO;
//import refrigerator.back.comment.adapter.repository.dao.CommentRepository;
//import refrigerator.back.comment.application.domain.Comment;
//import refrigerator.back.comment.application.dto.CommentDTO;
//import refrigerator.back.comment.application.dto.CommentListAndCountDTO;
//import refrigerator.back.comment.application.port.out.trash.CommentReadPort;
//import refrigerator.back.comment.application.port.out.trash.FindMyCommentListPort;
//import refrigerator.back.comment.application.port.out.trash.FindOneCommentPort;
//
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static refrigerator.back.comment.application.domain.CommentSortCondition.*;
//
//
//@Repository
//@RequiredArgsConstructor
//public class CommentLookUpAdapter implements CommentReadPort, FindOneCommentPort, FindMyCommentListPort {
//
//    private final CommentRepository repository;
//    private final OutCommentMapper mapper;
//
//    @Override
//    public CommentListAndCountDTO<CommentDTO> findCommentPreviewList(Long recipeId, int size) {
////        Page<OutCommentDTO> result = repository.findCommentPreviewList(recipeId, PageRequest.of(0, size));
////
////        return CommentListAndCountDTO.<CommentDTO>builder()
////                .comments(mapping(result.getContent()))
////                .count(Long.valueOf(result.getTotalElements()).intValue())
////                .build();
//        return null;
//    }
//
//    @Override
//    public List<CommentDTO> findCommentListByHeart(Long recipeId, String memberId, int page, int size) {
//        return mapping(repository
//                .findCommentList(recipeId, memberId, PageRequest.of(page, size), HEART));
//    }
//
//    @Override
//    public List<CommentDTO> findCommentListByDate(Long recipeId, String memberId, int page, int size) {
//        return mapping(repository
//                .findCommentList(recipeId, memberId, PageRequest.of(page, size), DATE));
//    }
//
//    @Override
//    public Optional<Comment> findCommentById(Long commentId) {
//        return repository.findByCommentID(commentId);
//    }
//
//
//    @Override
//    public List<CommentDTO> findMyComments(String memberId, Long recipeId) {
//        return mapping(repository
//                .findMyCommentList(memberId, recipeId));
//    }
//
//    private List<CommentDTO> mapping(List<OutCommentDTO> result) {
//        return result.stream()
//                .map(mapper::toCommentDto)
//                .collect(Collectors.toList());
//    }
//
//}
