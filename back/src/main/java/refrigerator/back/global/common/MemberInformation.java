package refrigerator.back.global.common;

import org.springframework.security.core.context.SecurityContextHolder;

public class MemberInformation {

    public static String getMemberEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
