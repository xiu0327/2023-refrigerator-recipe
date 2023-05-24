package refrigerator.back.identification.infra.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class IdentificationPrefixedKeySerializer implements RedisSerializer<String> {

    private final String IDENTIFICATION_PREFIX = "IDENTIFICATION::";

    @Override
    public byte[] serialize(String key) throws SerializationException {
        String prefixedKey = IDENTIFICATION_PREFIX + key;
        return prefixedKey.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null){
            return null;
        }
        String key = new String(bytes, StandardCharsets.UTF_8);
        if (key.startsWith(IDENTIFICATION_PREFIX)){
            return key.substring(IDENTIFICATION_PREFIX.length());
        }
        return key;
    }
}
