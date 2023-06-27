package refrigerator.back.authentication.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@RedisHash(value = "test")
public class RedisTestDto {
    @Id
    private String id;
    private String test;
}
