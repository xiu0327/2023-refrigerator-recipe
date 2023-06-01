package refrigerator.back.global.common;

import org.springframework.stereotype.Component;

@Component
public class MakeRedisKeyImpl implements MakeRedisKey{
    @Override
    public String makeKey(String typeName, String key) {
        return typeName + ":" + key;
    }
}
