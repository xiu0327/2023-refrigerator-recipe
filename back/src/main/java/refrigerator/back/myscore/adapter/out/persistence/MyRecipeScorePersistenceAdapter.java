//package refrigerator.back.myscore.adapter.out.persistence;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import refrigerator.back.global.exception.BusinessException;
//import refrigerator.back.myscore.adapter.out.MyRecipeScoreMapper;
//import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;
//import refrigerator.back.myscore.adapter.out.repository.MyRecipeScoreRepository;
//import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;
//import refrigerator.back.myscore.application.port.out.MyRecipeScoreReadPort;
//import refrigerator.back.myscore.application.port.out.MyRecipeScoreWritePort;
//import refrigerator.back.recipe.exception.RecipeExceptionType;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//@RequiredArgsConstructor
//public class MyRecipeScorePersistenceAdapter implements MyRecipeScoreReadPort, MyRecipeScoreWritePort {
//
//    @Autowired MyRecipeScoreRepository repository;
//    @Autowired MyRecipeScoreMapper myRecipeScoreMapper;
//
//    @Override
//    public MyRecipeScoreDomain getMyRecipeScoreByID(Long scoreID) {
//        MyRecipeScore entity = repository.findById(scoreID)
//                .orElseThrow(() -> new BusinessException(RecipeExceptionType.NOT_FOUND_RECIPE));
//        return myRecipeScoreMapper.toDomain(entity);
//    }
//
//    @Override
//    public MyRecipeScoreDomain getMyRecipeScore(String memberID, Long recipeID) {
//        MyRecipeScore entity = repository.findByMemberIDAndRecipeID(memberID, recipeID)
//                .orElseThrow(() -> new BusinessException(RecipeExceptionType.NOT_FOUND_RECIPE));
//        return myRecipeScoreMapper.toDomain(entity);
//    }
//
//    @Override
//    public List<MyRecipeScoreDomain> getMyRecipeScoreList(String memberID, int page, int size) {
//        return null;
//    }
//
//    @Override
//    public void create(String memberID, Long recipeID, double score) {
//
//    }
//
//    @Override
//    public void update(Long scoreID, double newScore) {
//
//    }
//}
