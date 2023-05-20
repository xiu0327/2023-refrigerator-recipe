package refrigerator.back.global.common;

public interface MakeRedisKey {
    String makeKey(String typeName, String key);
}
