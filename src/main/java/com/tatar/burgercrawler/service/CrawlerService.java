package com.tatar.burgercrawler.service;

import com.tatar.burgercrawler.model.ApiResponse;
import com.tatar.burgercrawler.model.BurgerUrl;
import com.tatar.burgercrawler.util.HtmlUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CrawlerService {

    private final BurgerAPIService burgerAPIService;

    public CrawlerService(BurgerAPIService burgerAPIService) {
        this.burgerAPIService = burgerAPIService;
    }

    /**
     * Crawls the Foursquare page of the given venue for photos. Works asynchronously.
     *
     * @param venueId   The venue’s  Foursquare id.
     * @param venueName The venue’s  Foursquare name.
     * @return a CompletableFuture that contains the response of the ML API
     */
    @Async("taskExecutor")
    public CompletableFuture<ApiResponse> getPhotoList(String venueId, String venueName) {

        BurgerUrl response = burgerAPIService.recognizeLatestBurgerImage(HtmlUtil.getPhotoUrlList(venueId, venueName));

        String latestBurgerUrl = response.getUrlWithBurger();

        return CompletableFuture.completedFuture(new ApiResponse(venueName, latestBurgerUrl));
    }
}
