package refrigerator.back.testdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;
import refrigerator.back.comment.application.port.in.heart.AddCommentHeartUseCase;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.mybookmark.application.port.in.AddBookmarkUseCase;
import refrigerator.back.myscore.application.port.in.CreateMyScoreUseCase;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
@Transactional
@Commit
public class TestDataCommit {

    @Autowired JoinUseCase joinUseCase;
    @Autowired WriteCommentUseCase writeCommentUseCase;
    @Autowired RegisterIngredientUseCase registerIngredientUseCase;
    @Autowired AddBookmarkUseCase addBookmarkUseCase;
    @Autowired AddCommentHeartUseCase addCommentHeartUseCase;
    @Autowired CreateCommentHeartNotificationUseCase notificationUseCase;
    @Autowired CreateMyScoreUseCase createMyScoreUseCase;
    @Autowired EntityManager em;

    String jkEmail = "jktest101@gmail.com";
    String msEmail = "mstest102@gmail.com";

    /*
    * 회원 생성
    * 100명의 정상 회원 생성
    * */
    void initMember() {
        for (int i = 1; i <= 100; i++) {
            joinUseCase.join(
                    getEmail(i),
                    "password123!",
                    "닉네임" + i
            );
        }
    }

    /*
     * 관리자 회원 등록
     * */
    void initAdminData(){
        joinUseCase.join(jkEmail, "password123!", "진경이");
        joinUseCase.join(msEmail, "password123!", "명선이");
    }

    /*
    * 회원 댓글 생성
    * 모든 레시피에 대해 20개의 댓글 생성
    * 댓글 작성하는 회원의 이메일은 1~100 사이의 숫자 중 랜덤
    * */
    void initComment(){
        List<Long> ids = em.createQuery("select r.recipeID from Recipe r", Long.class)
                .getResultList();
        for (Long id : ids) {
            int random = new Random().nextInt(99) + 1;
            for (int i = 0 ; i < 20 ; i++){
                writeCommentUseCase.write(
                        id,
                        getEmail(random),
                        "레시피 댓글" + UUID.randomUUID().toString().substring(0, 7)
                );
            }
        }
    }

    /*
    * 특정 회원의 식재료 등록
    * 특정 회원은 저장방식 별 30개씩 식재료 등록
    * 식재료의 유통기한은 12월 랜덤 일자
    * */
    void initIngredientData(){
        List<RegisteredIngredient> data = em.createQuery("select i from RegisteredIngredient i", RegisteredIngredient.class)
                .getResultList();
        Random random = new Random();
        for (IngredientStorageType value : IngredientStorageType.values()) {
            for (int i = 0 ; i < 30 ; i++){
                int imageRandom = random.nextInt(16) + 1;
                registerIngredientUseCase.registerIngredient(
                        data.get(i).getName(),
                        LocalDate.of(2023, 12, random.nextInt(29) + 1),
                        45.0,
                        data.get(i).getUnit(),
                        value,
                        imageRandom,
                        jkEmail
                );
                registerIngredientUseCase.registerIngredient(
                        data.get(i).getName(),
                        LocalDate.of(2023, 12, random.nextInt(29) + 1),
                        45.0,
                        data.get(i).getUnit(),
                        value,
                        imageRandom,
                        msEmail
                );
            }
        }
    }

    /*
    * 특정 회원이 레시피 30개에 대하여 별점을 남김
    * 별점 범위는 1 ~ 5 사이의 랜덤 숫자
    * */
    void initMyScoreData(){
        List<Long> ids = em.createQuery("select r.recipeID from Recipe r", Long.class)
                .getResultList();
        Random random = new Random();
        for (int i = 30 ; i < 60 ; i++){
            createMyScoreUseCase.cooking(
                    jkEmail,
                    ids.get(i),
                    Integer.valueOf(random.nextInt(4) + 1).doubleValue()
            );
            createMyScoreUseCase.cooking(
                    msEmail,
                    ids.get(i),
                    Integer.valueOf(random.nextInt(4) + 1).doubleValue()
            );
        }
    }


    /*
    * 특정 회원이 레시피 20개(1~10)를 북마크
    * */
    void initBookmarkData(){
        for (long i = 1L ; i <= 20L ; i++){
            addBookmarkUseCase.add(jkEmail, i);
            addBookmarkUseCase.add(msEmail, i);
        }
    }

    /*
    * 경우 1. jk recipeId = 1에 댓글을 남김 -> ms가 댓글에 좋아요를 누름 -> jk 에게 알림 전송
    * 경우 2. ms recipeId = 1에 댓글을 남김 -> jk가 댓글을 좋아요 누름 -> ms 에게 알림 전송
    * */
    void initCommentHeartData(){
        // 경우 1
        Long jkCommentId = writeCommentUseCase.write(1L, jkEmail, "안녕하세요 명선씨");
        addCommentHeartUseCase.addHeart(msEmail, jkCommentId);
        notificationUseCase.createCommentHeartNotification(msEmail, jkCommentId);
        // 경우 2
        Long msCommentId = writeCommentUseCase.write(1L, msEmail, "안녕하세요 진경씨");
        addCommentHeartUseCase.addHeart(jkEmail, msCommentId);
        notificationUseCase.createCommentHeartNotification(jkEmail, msCommentId);
    }

    private String getEmail(int i) {
        return "testemail" + i + "@gmail.com";
    }

}
