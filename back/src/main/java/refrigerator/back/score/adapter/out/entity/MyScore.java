package refrigerator.back.score.adapter.out.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_score_member")
@Getter
@NoArgsConstructor
public class MyScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long scoreID;

    @Column(name = "member_email", nullable = false, length = 300)
    private String memberID;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeID;

    @Column(name = "score")
    private double score;

    @CreatedDate
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;


}
