package com.tatar.burgercrawler.service;

import com.tatar.burgercrawler.constant.ApiConstants;
import com.tatar.burgercrawler.model.Venue;
import com.tatar.burgercrawler.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FsAPIService {

    private static final Logger logger = LoggerFactory.getLogger(FsAPIService.class);

    private final RestTemplate restTemplate;

    public FsAPIService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Retrieves venues from Foursquare API
     *
     * @param offset value for pagination. To be sent to Foursquare API, not required.
     * @return list of Venue objects
     */
    public List<Venue> getVenues(String offset) {

        String requestUrl = ApiConstants.REQUEST_URL;

        if (offset != null) {
            requestUrl = requestUrl + ApiConstants.OFFSET + offset;
        }

        logger.info("Request URL: " + requestUrl);

        String rawJson = restTemplate.getForObject(requestUrl, String.class); // raw JSON response from Foursquare API

        logger.info("Response: " + rawJson);

        // return a list of Venue(name and id only) from response JSON
        return JsonUtil.getVenueList(rawJson);
    }

}
