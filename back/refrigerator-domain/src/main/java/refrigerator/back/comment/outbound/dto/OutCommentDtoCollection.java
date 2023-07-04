package refrigerator.back.comment.outbound.dto;

import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.outbound.mapper.OutCommentMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OutCommentDtoCollection {

    private final List<OutCommentDTO> comments;

    public OutCommentDtoCollection(List<OutCommentDTO> comments) {
        this.comments = comments;
    }

    public List<CommentDto> mapping(OutCommentMapper mapper){
        return comments.stream().map(comment -> comment.mapping(mapper))
                .collect(Collectors.toList());
    }

    public boolean isExist(){
        return comments.stream().noneMatch(comment -> comment.equals(OutCommentDTO.builder().build()));
    }
    public int getSize(){
        return comments.size();
    }
}
