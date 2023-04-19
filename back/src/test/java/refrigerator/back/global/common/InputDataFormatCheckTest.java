package refrigerator.back.global.common;

import org.junit.jupiter.api.Test;
import refrigerator.back.member.adapter.in.dto.request.MemberJoinRequestDTO;

class InputDataFormatCheckTest {

    @Test
    void 이메일_입력_형식_테스트(){
        String email = "email123@gmail.com";
        String password = "password123!";
        String nickname = "닉네임";

        MemberJoinRequestDTO result = MemberJoinRequestDTO.builder()
                .email(email)
                .password(password)
                .nickname(nickname).build();

        result.check();

    }

}