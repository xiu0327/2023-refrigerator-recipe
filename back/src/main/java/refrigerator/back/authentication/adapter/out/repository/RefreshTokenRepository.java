package refrigerator.back.authentication.adapter.out.repository;

public interface RefreshTokenRepository {
    String getData(String key);
    void setData(String key, String value, long duration);
    void removeData(String key);
}
