package ru.panic.mainservice.util;

import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    public String extractValueFromDefaultCookie(String cookieValue, String key) {
        String[] cookies = cookieValue.split(";");
        for (String cookie : cookies) {
            if (cookie.trim().startsWith(key)) {
                return cookie.split("=")[1];
            }
        }
        return null;
    }
}
