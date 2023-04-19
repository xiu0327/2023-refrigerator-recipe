package refrigerator.back.comment.application.port.in.people;


import java.util.List;

public interface FindLikedPeopleListUseCase {
    List<Long> findLikedPeople(String memberId);
}
