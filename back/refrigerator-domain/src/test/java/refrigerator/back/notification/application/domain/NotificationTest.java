package refrigerator.back.notification.application.domain;

import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BasicHttpMethod;

import static org.assertj.core.api.Assertions.*;


class NotificationTest {

    @Test
    void create() {
        Notification notification = Notification.create(
                NotificationType.HEART,
                "/recipe/comment?recipeId=1&commentID=1",
                "email123@gmain.com",
                BasicHttpMethod.GET.name()
        );

        //CreateDate
        //id
        assertThat(notification.getType()).isEqualTo(NotificationType.HEART);
        assertThat(notification.getPath()).isEqualTo("/recipe/comment?recipeId=1&commentID=1");
        assertThat(notification.getMemberId()).isEqualTo("email123@gmain.com");
        assertThat(notification.getMethod()).isEqualTo(BasicHttpMethod.GET.name());
    }

    @Test
    void createCommentHeartMessage() {
        Notification notification = new Notification();
        notification.createCommentHeartMessage("email123@gmain.com");

        assertThat(notification.getMessage()).isEqualTo("email123@gmain.com 님이 좋아요를 눌렀습니다.");
    }

    @Test
    void createExpirationDateMessage() {
        Notification notification = new Notification();
        notification.createExpirationDateMessage("사과", 2l, 3);
        assertThat(notification.getMessage()).isEqualTo("사과 외 1개 식재료의 소비기한이 3일 남았습니다. 식재료 확인하러가기!");

        notification.createExpirationDateMessage("사과", 1l, 3);
        assertThat(notification.getMessage()).isEqualTo("사과의 소비기한이 3일 남았습니다. 식재료 확인하러가기!");

        notification.createExpirationDateMessage("사과", 1l, 3);
        assertThat(notification.getMessage()).isEqualTo("사과의 소비기한이 3일 남았습니다. 식재료 확인하러가기!");
    }

    @Test
    void createIngredientMessage() {
        Notification notification = new Notification();
        notification.createIngredientMessage("사과");
        assertThat(notification.getMessage()).isEqualTo("회원님이 요청했던 사과를 이제 냉장고에 담을 수 있습니다. (식재료 추가하기)");
    }

    @Test
    void createNoticeMessage() {
        Notification notification = new Notification();
        notification.createNoticeMessage("안녕하세요");
        assertThat(notification.getMessage()).isEqualTo("공지사항이 추가되었어요! '안녕하세요'");

    }
}