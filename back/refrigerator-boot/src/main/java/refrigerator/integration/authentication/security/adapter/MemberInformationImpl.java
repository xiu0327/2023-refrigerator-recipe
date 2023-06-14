package refrigerator.integration.authentication.security.adapter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;

@Component
public class MemberInformationImpl implements GetMemberEmailUseCase {

    public String getMemberEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
