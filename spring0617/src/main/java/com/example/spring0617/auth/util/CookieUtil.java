package com.example.spring0617.auth.util;

import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;

public class CookieUtil {
	
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
    	ResponseCookie cookie = ResponseCookie.from(name, value)
            	.path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .domain(domain)
                .maxAge(maxAge)
                .build();
    	
        response.addHeader("Set-Cookie", cookie.toString());
    }
    
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                	ResponseCookie rcookie = ResponseCookie.from(name, "")
                        	.path("/")
                            .sameSite("None")
                            .httpOnly(true)
                            .secure(true)
                            .domain(domain)
                            .maxAge(0)
                            .build();
                    
                    response.addHeader("Set-Cookie", rcookie.toString());
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
