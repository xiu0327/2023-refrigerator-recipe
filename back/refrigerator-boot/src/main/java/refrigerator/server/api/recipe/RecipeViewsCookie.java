package refrigerator.server.api.recipe;

import lombok.Getter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class RecipeViewsCookie {

    private final Cookie[] cookies;

    public RecipeViewsCookie(Cookie[] cookies) {
        this.cookies = cookies;
    }

    private final int validTime = 24 * 60 * 60;
    private final String cookieName = "Recipe-View";


    public boolean isViewed(Long recipeId){
        if (cookies != null){
            return Arrays.stream(cookies).anyMatch(cookie ->
                    cookie.getName().equals(cookieName) && cookie.getValue().equals(String.valueOf(recipeId)));
        }
        return false;
    }

    public void addViewCookie(Long recipeId, HttpServletResponse response){
        response.addCookie(createCookie(recipeId));
    }

    private Cookie createCookie(Long recipeId){
        Cookie cookie = new Cookie(cookieName, String.valueOf(recipeId));
        cookie.setPath("/api/recipe");
        cookie.setMaxAge(validTime);
        cookie.setHttpOnly(true);
        return cookie;
    }

}
