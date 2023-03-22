package refrigerator.back.myscore.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import refrigerator.back.myscore.adapter.out.repository.query.MyScoreQueryRepository;
import refrigerator.back.myscore.application.domain.MyScore;

import java.util.Optional;

public interface MyScoreRepository extends JpaRepository<MyScore, Long>, MyScoreQueryRepository {
    @Query("select rs from MyScore rs where rs.memberID= :memberID and rs.recipeID= :recipeID")
    Optional<MyScore> findMyRecipeScoreByMemberIdAndRecipeId(@Param("memberID") String memberID, @Param("recipeID") Long recipeID);
}
