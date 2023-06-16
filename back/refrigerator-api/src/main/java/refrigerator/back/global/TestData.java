package refrigerator.back.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.notification.application.domain.Notification;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TestData {

    public final static String MEMBER_EMAIL = "email123@naver.com";
    public final static String MEMBER_PASSWORD = "password123!";
    public final static String MEMBER_NICKNAME = "닉네임뿅";
    private final EntityManager em;

    @Transactional
    public void createMember(){
        Member member = Member.builder()
                .email(MEMBER_EMAIL)
                .password(MEMBER_PASSWORD)
                .nickname(MEMBER_NICKNAME)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .memberStatus(MemberStatus.STEADY_STATUS).build();
        em.persist(member);
        em.flush();
        em.clear();
    }

    @Transactional
    public String createMemberByEmail(String email){
        Member member = Member.builder()
                .email(email)
                .password(MEMBER_PASSWORD)
                .nickname(MEMBER_NICKNAME)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .memberStatus(MemberStatus.STEADY_STATUS).build();
        em.persist(member);
        em.flush();
        em.clear();
        return member.getEmail();
    }

    @Transactional
    public String createMemberByEmailAndNickname(String email, String nickname){
        Member member = Member.builder()
                .email(email)
                .password(MEMBER_PASSWORD)
                .nickname(nickname)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .memberStatus(MemberStatus.STEADY_STATUS).build();
        em.persist(member);
        em.flush();
        em.clear();
        return member.getEmail();
    }

    @Transactional
    public String createMemberByEmailAndPassword(String email, String password){
        Member member = Member.builder()
                .email(email)
                .password(password)
                .nickname(MEMBER_NICKNAME)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .memberStatus(MemberStatus.STEADY_STATUS).build();
        em.persist(member);
        em.flush();
        em.clear();
        return member.getEmail();
    }

    @Transactional
    public Long createMyRecipeScore(String memberId, Long recipeId, Double score){
        MyScore myRecipeScore = MyScore.builder()
                .memberID(memberId)
                .recipeID(recipeId)
                .score(score).build();
        em.persist(myRecipeScore);
        em.flush();
        em.clear();
        return myRecipeScore.getScoreID();
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email){
        return em.createQuery("select m from Member m where m.email= :email", Member.class)
                .setParameter("email", email)
                .getResultList().stream().findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER));
    }

    @Transactional(readOnly = true)
    public CommentHeartPeople findLikedPeopleList(String memberId, Long commentId){
        return em.createQuery("select p from CommentHeartPeople p where p.memberId= :memberId and p.commentId= :commentId", CommentHeartPeople.class)
                .setParameter("memberId", memberId)
                .setParameter("commentId", commentId)
                .getResultList().stream().findAny()
                .orElseThrow(() -> new RuntimeException("좋아요 누른  사람 찾을 수 없음"));
    }

    @Transactional(readOnly = true)
    public Notification findNotificationById(Long id){
        return em.find(Notification.class, id);
    }

    public String makeTokenHeader(String token){
        return "Bearer " + token;
    }

    public Long createIngredient(String ingredientName, String memberId){
        Ingredient entity = Ingredient.create(
                ingredientName,
                LocalDate.now(),
                70.0,
                "g",
                IngredientStorageType.FRIDGE,
                1,
                memberId
        );
        em.persist(entity);
        return entity.getId();
    }

    public Long createIngredientDetail(String ingredientName, Integer days, String memberId) {
        Ingredient entity = Ingredient.create(
                ingredientName,
                LocalDate.now().minusDays(days),
                70.0,
                "g",
                IngredientStorageType.FRIDGE,
                1,
                memberId
        );
        em.persist(entity);
        return entity.getId();
    }

    public Comment createComment(String memberId){
        Comment comment = Comment.write(1L, memberId, "내용");
        em.persist(comment);
        return comment;
    }


    public void createIngredientWithDate(String ingredientName, String memberId, LocalDate date){
        em.persist(Ingredient.create(
                ingredientName,
                date,
                70.0,
                "g",
                IngredientStorageType.FREEZER,
                1,
                memberId
        ));
    }

}
