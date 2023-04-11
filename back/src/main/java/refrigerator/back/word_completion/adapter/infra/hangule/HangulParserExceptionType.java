package refrigerator.back.word_completion.adapter.infra.hangule;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@AllArgsConstructor
public class HangulParserExceptionType implements BasicExceptionType {

    private final HangulParserException exception;

    @Override
    public String getErrorCode() {
        return "HANGUL_PARSER_EXCEPTION";
    }

    @Override
    public String getMessage() {
        return exception.getMessage();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
