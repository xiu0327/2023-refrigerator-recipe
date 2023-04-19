package refrigerator.back.identification.application.port.out;

public interface IdentificationRedisPort {
    String getData(String key);
    void setData(String key, String value, long duration);
    void deleteData(String key);
}
