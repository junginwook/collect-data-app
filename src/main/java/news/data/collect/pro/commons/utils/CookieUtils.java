package news.data.collect.pro.commons.utils;

import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class CookieUtils {

    /**
     * 쿠기 조회
     */
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }

            }
        }

        return Optional.empty();
    }

    /**
     * 쿠키에 값 추가
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 쿠키 삭제
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }

            }
        }
    }

    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }

}
