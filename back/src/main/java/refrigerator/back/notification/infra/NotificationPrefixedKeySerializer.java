package refrigerator.back.notification.infra;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class NotificationPrefixedKeySerializer implements RedisSerializer<String> {

    private final String NOTIFICATION_PREFIX = "NOTIFICATION::";

    @Override
    public byte[] serialize(String key) throws SerializationException {
        String prefixedKey = NOTIFICATION_PREFIX + key;
        return prefixedKey.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null){
            return null;
        }
        String key = new String(bytes, StandardCharsets.UTF_8);
        if (key.startsWith(NOTIFICATION_PREFIX)){
            return key.substring(NOTIFICATION_PREFIX.length());
        }
        return key;
    }
}
