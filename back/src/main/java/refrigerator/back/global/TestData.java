package refrigerator.back.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.adapter.out.entity.MemberEntity;
import refrigerator.back.member.application.domain.MemberDomain;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.recipe.adapter.out.entity.*;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;
import refrigerator.back.recipe.application.domain.value.RecipeType;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestData {

    public final static String MEMBER_EMAIL = "email123@naver.com";
    public final static String MEMBER_PASSWORD = "password123!";
    public final static String MEMBER_NICKNAME = "닉네임뿅";
    private final EntityManager em;

    @Transactional
    public void createMember(){
        MemberEntity member = MemberEntity.builder()
                .email(MEMBER_EMAIL)
                .password(MEMBER_PASSWORD)
                .nickname(MEMBER_NICKNAME)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE.getName())
                .memberStatus(MemberStatus.STEADY_STATUS.getStatusCode()).build();
        em.persist(member);
        em.flush();
        em.clear();
    }

    @Transactional(readOnly = true)
    public MemberEntity findMemberByEmail(String email){
        return em.createQuery("select m from MemberEntity m where m.email= :email", MemberEntity.class)
                .setParameter("email", email)
                .getResultList().stream().findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER));
    }

    public MemberDomain createMemberDomain(){
        return MemberDomain.builder()
                .memberID(1L)
                .email("email123@naver.com")
                .password("password123!")
                .nickname("닉네임")
                .joinDate(LocalDateTime.now())
                .memberStatus(MemberStatus.STEADY_STATUS)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .build();
    }

    public MemberEntity createMemberEntity(){
        return MemberEntity.builder()
                .id(1L)
                .email("email123@naver.com")
                .password("password123!")
                .nickname("닉네임")
                .memberStatus(MemberStatus.STEADY_STATUS.getStatusCode())
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE.getName())
                .build();
    }

    public RecipeDomain getRecipeDomain(){
        return RecipeDomain.builder()
                .recipeID(1L)
                .image("image")
                .recipeName("레시피명")
                .difficulty(RecipeDifficulty.NO_LEVEL)
                .kcal(3)
                .servings(3)
                .description("설명")
                .bookmarks(2)
                .cookingTime(40)
                .person(2)
                .recipeCategory("카테고리")
                .recipeType(RecipeType.KOREA)
                .score(3.0)
                .recipeFoodType("음식타입")
                .views(0)
                .person(0)
                .build();
    }

    public RecipeIngredient getRecipeIngredientEntity(){
        return RecipeIngredient.builder()
                .ingredientID(1L)
                .recipeID(1L)
                .name("식재료명")
                .volume("용량")
                .transUnit("치환단위")
                .transVolume(3.2)
                .type("주재료").build();
    }

    public RecipeCourse getRecipeCourseEntity(){
        return RecipeCourse.builder()
                .courseID(1L)
                .recipeID(1L)
                .step(1)
                .explanation("설명")
                .image("이미지").build();
    }
}
