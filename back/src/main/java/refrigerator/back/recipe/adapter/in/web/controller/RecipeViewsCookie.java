package refrigerator.back.recipe.adapter.in.web.controller;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Data
public class RecipeViewsCookie {

    private int validTime = 24 * 60 * 60;
    private String cookieName;
    private boolean isViewed;

    public RecipeViewsCookie(Long recipeID) {
        this.cookieName = "viewed_" + recipeID;
        this.isViewed = false;
    }

    public boolean changeViewed(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)){
                    this.isViewed = true;
                    break;
                }
            }
        }
        return isViewed;
    }

    public Cookie createCookie(){
        Cookie cookie = new Cookie(cookieName, "true");
        cookie.setMaxAge(validTime);
        return cookie;
    }

}
