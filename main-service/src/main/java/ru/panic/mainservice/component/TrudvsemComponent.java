package ru.panic.mainservice.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.panic.mainservice.util.CookieUtil;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class TrudvsemComponent {

    private final RestTemplate restTemplate;
    private final CookieUtil cookieUtil;
    private String JSESSIONID;
    private final String TRUDVSEM_START_URL = "https://trudvsem.ru";

    @Scheduled(fixedDelay = 600000)
    public void updateJSESSIONID() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(TRUDVSEM_START_URL, HttpMethod.GET, entity, Void.class);

        HttpHeaders responseHeaders = response.getHeaders();

        if (responseHeaders.containsKey("Set-Cookie")) {
            String jsessionId = null;
            for (String setCookie : responseHeaders.get("Set-Cookie")) {
                if (setCookie.contains("JSESSIONID")) {
                    jsessionId = cookieUtil.extractValueFromDefaultCookie(setCookie, "JSESSIONID");
                    break;
                }
            }

            //Setting value to JSESSIONID

            JSESSIONID = jsessionId;
        } else {
            log.error("Set-Cookie header was not found when JSESSIONID was updated");
        }
    }
}
