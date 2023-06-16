package refrigerator.back.global.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BasicHttpStatus {

    NOT_FOUND(404),
    BAD_REQUEST(400),
    FORBIDDEN(403);

    private int code;
}
