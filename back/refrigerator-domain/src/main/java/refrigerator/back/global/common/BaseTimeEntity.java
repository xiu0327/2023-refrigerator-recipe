package refrigerator.back.global.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder // TODO : 오류날 수도 있으니 혹시나 남겨둠 (사용처 : MyBookmark, MyScore, Notice, Notification)
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createDate;

    public LocalDateTime getCreateDate() {
        return createDate;
    }

}
