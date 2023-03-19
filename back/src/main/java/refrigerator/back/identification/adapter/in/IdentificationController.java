package refrigerator.back.identification.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.identification.adapter.dto.CheckNumberRequestDTO;
import refrigerator.back.identification.adapter.dto.CheckNumberResponseDTO;
import refrigerator.back.identification.adapter.dto.SendNumberRequestDTO;
import refrigerator.back.identification.adapter.dto.SendNumberResponseDTO;
import refrigerator.back.identification.application.port.in.CheckNumberUseCase;
import refrigerator.back.identification.application.port.in.SendNumberUseCase;

@RestController
@RequiredArgsConstructor
public class IdentificationController {

    private final CheckNumberUseCase checkNumberUseCase;
    private final SendNumberUseCase sendNumberUseCase;
    @Value("${spring.mail.timeout}")
    private Long duration; // 5분

    @PostMapping("/api/identification/send")
    @ResponseStatus(HttpStatus.CREATED)
    public SendNumberResponseDTO send(@RequestBody SendNumberRequestDTO request){
        return SendNumberResponseDTO.builder()
                .code(sendNumberUseCase.sendAuthenticationNumber(request.getEmail(), duration))
                .build();
    }

    @PostMapping("/api/identification/check")
    public CheckNumberResponseDTO check(@RequestBody CheckNumberRequestDTO request){
        return CheckNumberResponseDTO.builder()
                .status(checkNumberUseCase.checkAuthenticationNumber(request.getInputCode(), request.getEmail()))
                .build();
    }
}
