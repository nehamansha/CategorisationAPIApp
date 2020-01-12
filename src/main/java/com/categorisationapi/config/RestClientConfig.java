package com.categorisationapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestClientConfig extends RestTemplate{

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        } else {
            interceptors = setUpInterceptors();
        }
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    private List<ClientHttpRequestInterceptor> setUpInterceptors() {
        List<ClientHttpRequestInterceptor> httpRequestInterceptors = new ArrayList<ClientHttpRequestInterceptor>();
        httpRequestInterceptors.add(new RestTemplateHeaderModifierInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        httpRequestInterceptors.add(new RestTemplateHeaderModifierInterceptor("X-Partner-Id", "X-Partner-Id"));
        httpRequestInterceptors.add(new RestTemplateHeaderModifierInterceptor("X-Api-Key", "apiKey"));
        httpRequestInterceptors.add(new RestTemplateHeaderModifierInterceptor("Authorization", "Authorization"));
        return httpRequestInterceptors;
    }
}
