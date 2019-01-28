package com.tatar.burgercrawler.service;

import com.tatar.burgercrawler.constant.ApiConstants;
import com.tatar.burgercrawler.model.BurgerUrl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BurgerAPIService {

    private static final Logger logger = LoggerFactory.getLogger(BurgerAPIService.class);

    private final RestTemplate restTemplate;

    public BurgerAPIService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BurgerUrl post(List<String> photoUrls) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject urlsJson = new JSONObject();
        urlsJson.put("urls", photoUrls);

        HttpEntity<String> entity = new HttpEntity<>(urlsJson.toString(), headers);

        BurgerUrl response = new BurgerUrl("");

        try {
            response = restTemplate.postForObject(ApiConstants.ML_BASE_URL, entity, BurgerUrl.class);
        } catch (HttpClientErrorException e) {
            logger.error(e.getStatusCode().toString());
        }

        return response;
    }
}
