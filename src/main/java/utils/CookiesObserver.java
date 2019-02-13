package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static utils.Const.COOKIE_NAME;

public class CookiesObserver {

    private final HttpServletRequest req;

    public CookiesObserver(HttpServletRequest req) {
        this.req = req;
    }

    public boolean isCookieSets() {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if (cook.getName().equals(COOKIE_NAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Cookie getUserCookie() {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if (cook.getName().equals(COOKIE_NAME)) {
                    return cook;
                }
            }
        }
        return null;
    }

}