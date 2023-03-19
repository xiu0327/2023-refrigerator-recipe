package refrigerator.back.myscore.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;

import java.util.List;
import java.util.Optional;

public interface MyRecipeScoreRepository extends JpaRepository<MyRecipeScore, Long>, MyRecipeScoreQueryRepository {

}
