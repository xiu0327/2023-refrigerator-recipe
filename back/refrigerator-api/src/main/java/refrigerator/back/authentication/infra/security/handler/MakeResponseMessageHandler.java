package refrigerator.back.authentication.infra.security.handler;


import refrigerator.back.global.exception.domain.BusinessException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MakeResponseMessageHandler {

    public static void makeResponseMessage(HttpServletResponse response, BusinessException e) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(
                "{\"error\": \""+ e.getBasicExceptionType().getErrorCode()+"\", \"message\" : \""+
                        e.getBasicExceptionType().getMessage()+"\"}");
    }
}
