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
public class VenueService {

    private static final Logger logger = LoggerFactory.getLogger(VenueService.class);

    private final RestTemplate restTemplate;

    public VenueService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Venue> getVenues() {
        String rawJson = restTemplate.getForObject(ApiConstants.REQUEST_URL, String.class); // raw JSON response from Foursquare API

        // return a list of Venue(name and id only) from response JSON
        return JsonUtil.getVenueList(rawJson);
    }

}
