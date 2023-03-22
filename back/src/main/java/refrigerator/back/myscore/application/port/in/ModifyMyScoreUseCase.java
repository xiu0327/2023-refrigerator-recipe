package refrigerator.back.myscore.application.port.in;

public interface ModifyMyScoreUseCase {
    void modify(Long scoreID, double newScore);
}
