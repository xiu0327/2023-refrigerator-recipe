package refrigerator.back.authentication.infra.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class TokenPrefixedKeySerializer implements RedisSerializer<String> {

    private final String TOKEN_PREFIX = "TOKEN::";

    @Override
    public byte[] serialize(String key) throws SerializationException {
        String prefixedKey = TOKEN_PREFIX + key;
        return prefixedKey.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null){
            return null;
        }
        String key = new String(bytes, StandardCharsets.UTF_8);
        if (key.startsWith(TOKEN_PREFIX)){
            return key.substring(TOKEN_PREFIX.length());
        }
        return key;
    }
}
