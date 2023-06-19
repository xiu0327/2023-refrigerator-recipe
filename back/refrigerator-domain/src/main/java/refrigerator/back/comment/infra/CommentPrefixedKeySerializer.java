package refrigerator.back.comment.infra;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class CommentPrefixedKeySerializer implements RedisSerializer<String> {

    private final String COMMENT_PREFIX = "COMMENT::";

    @Override
    public byte[] serialize(String key) throws SerializationException {
        String prefixedKey = COMMENT_PREFIX + key;
        return prefixedKey.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null){
            return null;
        }
        String key = new String(bytes, StandardCharsets.UTF_8);
        if (key.startsWith(COMMENT_PREFIX)){
            return key.substring(COMMENT_PREFIX.length());
        }
        return key;
    }
}
