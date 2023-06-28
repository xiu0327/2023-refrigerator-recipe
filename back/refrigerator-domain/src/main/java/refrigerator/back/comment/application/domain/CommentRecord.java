package refrigerator.back.comment.application.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
public class CommentRecord {

    @Column(name = "create_date")
    private LocalDateTime createDateTime;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDateTime;

    @Column(name = "modified_state", nullable = false)
    private Boolean modifiedState;

    @Column(name = "deleted_state")
    Boolean deletedState;

    public CommentRecord(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
        this.lastModifiedDateTime = createDateTime;
        this.deletedState = false;
        this.modifiedState = false;
    }

    public void renew(LocalDateTime lastModifiedDateTime){
        this.lastModifiedDateTime = lastModifiedDateTime;
        this.modifiedState = true;
    }

    public void enableDeleteStatus(){
        this.deletedState = true;
    }

    public LocalDateTime isModified(){
        if (modifiedState){
            return lastModifiedDateTime;
        }
        return null;
    }
}
