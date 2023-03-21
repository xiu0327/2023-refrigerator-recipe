package refrigerator.back.myscore.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;

import java.util.List;
import java.util.Optional;

public interface MyRecipeScoreRepository extends JpaRepository<MyRecipeScore, Long>, MyRecipeScoreQueryRepository {
    @Query("select rs from MyRecipeScore rs where rs.memberID= :memberID and rs.recipeID= :recipeID")
    Optional<MyRecipeScore> findMyRecipeScoreByMemberIdAndRecipeId(@Param("memberID") String memberID, @Param("recipeID") Long recipeID);
}
