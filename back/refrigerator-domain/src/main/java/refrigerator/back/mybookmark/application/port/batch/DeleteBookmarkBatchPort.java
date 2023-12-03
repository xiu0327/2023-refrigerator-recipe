package refrigerator.back.mybookmark.application.port.batch;

import refrigerator.back.global.jpa.WriteQueryResultType;

import java.util.List;

public interface DeleteBookmarkBatchPort {

    WriteQueryResultType deleteMyBookmark();
}
