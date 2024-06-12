package ru.panic.mainservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.panic.mainservice.api.payload.TrudvsemSearchCvRequest;
import ru.panic.mainservice.api.payload.TrudvsemSearchCvResponse;
import ru.panic.mainservice.api.payload.TrudvsemViewCvResponse;
import ru.panic.mainservice.component.TrudvsemComponent;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class TrudvsemApi {

    private final String TRUDVSEM_API_URL = "https://trudvsem.ru";

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final TrudvsemComponent trudvsemComponent;

    public TrudvsemSearchCvResponse searchCv(TrudvsemSearchCvRequest trudvsemSearchCvRequest) {
        UriComponentsBuilder builder;
        try {
            builder = UriComponentsBuilder.fromHttpUrl(TRUDVSEM_API_URL)
                    .path("/iblocks/_catalog/flat_filter_prr_search_cv/data")
                    .queryParam("page", trudvsemSearchCvRequest.page())
                    .queryParam("pageSize", trudvsemSearchCvRequest.pageSize())
                    .queryParam("orderColumn", trudvsemSearchCvRequest.orderColumn())
                    .queryParam("filter", URLDecoder.decode(URLEncoder.encode(objectMapper.writeValueAsString(trudvsemSearchCvRequest.filter()), StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        String finalUrl = builder.toUriString();

        System.out.println(finalUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + trudvsemComponent.getJSESSIONID());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TrudvsemSearchCvResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, TrudvsemSearchCvResponse.class);

        return response.getBody();
    }

    public TrudvsemViewCvResponse viewCv(String firstId, String secondId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + trudvsemComponent.getJSESSIONID());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TrudvsemViewCvResponse> response = restTemplate.exchange(TRUDVSEM_API_URL + "/iblocks/prr_cv_viewing/"
                + firstId + "/" + secondId, HttpMethod.GET, entity, TrudvsemViewCvResponse.class);

        return response.getBody();
    }

}
