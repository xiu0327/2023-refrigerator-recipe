package refrigerator.back.global.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BusinessException;

import static java.util.regex.Pattern.matches;

public abstract class InputDataFormatCheck {
    @JsonIgnore
    protected final String NICKNAME_REGEX = "^[가-힣]{3,10}|[a-zA-Z]+$";
    @JsonIgnore
    protected final String EMAIL_REGEX = "^[a-zA-Z0-9_]+@[a-zA-Z]+\\.[a-zA-Z]+$";
    @JsonIgnore
    protected final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{4,16}$";

    public abstract void check();

    protected void inputCheck(String nameRegex, String name, BasicExceptionType exceptionType) {
        if(!matches(nameRegex, name))
            throw new BusinessException(exceptionType);
    }
}
