package refrigerator.back.mybookmark.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.mybookmark.application.port.batch.DeleteBookmarkBatchPort;
import refrigerator.back.mybookmark.outbound.repository.query.MyBookmarkDeleteQueryRepository;

@Component
@RequiredArgsConstructor
public class MyBookmarkBatchAdapter implements DeleteBookmarkBatchPort {

    private final MyBookmarkDeleteQueryRepository myBookmarkDeleteQueryRepository;


    @Override
    public WriteQueryResultType deleteMyBookmark() {
        return myBookmarkDeleteQueryRepository.deleteMyBookmarkByDeletedStatus();
    }
}
