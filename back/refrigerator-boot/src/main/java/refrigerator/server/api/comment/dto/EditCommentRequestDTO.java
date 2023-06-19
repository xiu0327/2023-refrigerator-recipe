package refrigerator.server.api.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentRequestDTO {

    @NotNull
    private Long commentID;

    @NotBlank
    private String content;
}
