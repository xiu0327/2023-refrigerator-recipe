package refrigerator.back.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class BasicExceptionFormat {
    private String code;
    private String message;

    public static BasicExceptionFormat create(BasicExceptionType baseExceptionType){
        return new BasicExceptionFormat(baseExceptionType.getErrorCode(), baseExceptionType.getMessage());
    }

    public BasicExceptionFormat(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
