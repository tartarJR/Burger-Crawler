package com.tatar.burgercrawler.service;

import com.tatar.burgercrawler.constant.ApiConstants;
import com.tatar.burgercrawler.constant.JsonConstants;
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

    /**
     * Sends a post request to ML API for recognizing burgers
     *
     * @param photoUrls URLs of burger photos for a specific Venue.
     * @return the ML API response. Burger photo URL or Not Found
     */
    public BurgerUrl recognizeLatestBurgerImage(List<String> photoUrls) {

        BurgerUrl response = new BurgerUrl("");

        if (!photoUrls.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject urlsJson = new JSONObject();
            urlsJson.put(JsonConstants.ML_API_URL_LIST_NAME, photoUrls);

            HttpEntity<String> entity = new HttpEntity<>(urlsJson.toString(), headers);

            try {
                response = restTemplate.postForObject(ApiConstants.ML_BASE_URL, entity, BurgerUrl.class);
            } catch (HttpClientErrorException e) {
                logger.error(e.getStatusCode().toString());
            }
        }

        return response;
    }
}
