package refrigerator.back.myscore.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.application.dto.InMyScoreDTO;
import refrigerator.back.myscore.application.dto.InMyScoreListDTO;
import refrigerator.back.myscore.application.dto.InMyScorePreviewDTO;
import refrigerator.back.myscore.adapter.mapper.MyScoreDtoMapper;
import refrigerator.back.myscore.adapter.out.dto.OutMyScorePreviewDTO;
import refrigerator.back.myscore.adapter.out.repository.MyScorePersistenceRepository;
import refrigerator.back.myscore.adapter.out.repository.MyScoreSelectQueryRepository;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.myscore.application.port.out.MyScoreReadPort;
import refrigerator.back.myscore.application.port.out.MyScoreWritePort;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QueryMyScoreDataAdapter implements MyScoreReadPort, MyScoreWritePort {

    private final MyScoreSelectQueryRepository QueryRepository;
    private final MyScorePersistenceRepository persistenceRepository;
    private final MyScoreDtoMapper mapper;

    @Override
    public Optional<MyScore> findByMemberIdAndRecipeId(String memberID, Long recipeID) {
        return persistenceRepository.findMyRecipeScoreByMemberIdAndRecipeId(memberID, recipeID);
    }

    @Override
    public List<InMyScoreDTO> getMyScoreList(String memberID, int page, int size) {
        return QueryRepository.selectMyRecipeScoreList(memberID, PageRequest.of(page, size))
                .stream().map(mapper::toInMyScoreDto)
                .collect(Collectors.toList());

    }

    @Override
    public InMyScoreListDTO<InMyScorePreviewDTO> getMyScorePreview(String memberID, int size) {
        Page<OutMyScorePreviewDTO> result = QueryRepository.selectScorePreview(memberID, PageRequest.of(0, size));
        List<InMyScorePreviewDTO> scores = result.getContent()
                .stream().map(mapper::toInMyScorePreviewDto)
                .collect(Collectors.toList());
        return new InMyScoreListDTO<>(scores, Long.valueOf(result.getTotalElements()).intValue());
    }

    @Override
    public Optional<MyScore> findById(Long scoreID) {
        return persistenceRepository.findById(scoreID);
    }

    @Override
    public Long save(MyScore score) {
        persistenceRepository.save(score);
        return score.getScoreID();
    }
}
