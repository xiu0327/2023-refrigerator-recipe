package refrigerator.back.myscore.application.port.out;


import refrigerator.back.myscore.application.domain.MyScore;

public interface MyScoreWritePort {
    Long save(MyScore score);
}
