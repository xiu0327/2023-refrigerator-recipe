package refrigerator.back.authentication.adapter.out.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TokenRedisDTO {

    private String key;
    private String value;
    private Long ttl;

    private final String prefix = "TOKEN";

    private TokenRedisDTO(String key, String value, Long ttl) {
        this.key = key;
        this.value = value;
        this.ttl = ttl;
    }

    public static TokenRedisDTO createToken(String key, String value, long ttl){
        return new TokenRedisDTO("TOKEN::" + key , value, ttl);
    }
}
