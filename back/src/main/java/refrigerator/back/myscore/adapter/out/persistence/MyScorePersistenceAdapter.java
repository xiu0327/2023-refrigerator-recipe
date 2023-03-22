package refrigerator.back.myscore.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScorePreviewDTO;
import refrigerator.back.myscore.adapter.mapper.MyScoreDtoMapper;
import refrigerator.back.myscore.adapter.out.repository.MyScoreRepository;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.myscore.application.port.out.MyScoreReadPort;
import refrigerator.back.myscore.application.port.out.MyScoreWritePort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MyScorePersistenceAdapter implements MyScoreReadPort, MyScoreWritePort {

    @Autowired
    MyScoreRepository repository;
    @Autowired MyScoreDtoMapper mapper;

    @Override
    public Optional<MyScore> findByMemberIdAndRecipeId(String memberID, Long recipeID) {
        return repository.findMyRecipeScoreByMemberIdAndRecipeId(memberID, recipeID);
    }

    @Override
    public List<InMyScoreDTO> getMyScoreList(String memberID, int page, int size) {
        return repository.findMyRecipeScoreList(memberID, PageRequest.of(page, size))
                .stream().map(score -> mapper.toInMyScoreDto(score, score.getScore().calculateScore()))
                .collect(Collectors.toList());

    }

    @Override
    public List<InMyScorePreviewDTO> getMyScorePreview(String memberID) {
        return repository.findScorePreview(memberID)
                .stream().map(mapper::toInMyScorePreviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MyScore> findById(Long scoreID) {
        return repository.findById(scoreID);
    }

    @Override
    public Long save(MyScore score) {
        repository.save(score);
        return score.getScoreID();
    }
}
