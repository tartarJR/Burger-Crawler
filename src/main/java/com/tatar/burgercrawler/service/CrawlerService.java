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

    @Async("taskExecutor")
    public CompletableFuture<ApiResponse> getPhotoList(String venueId, String venueName) {

        BurgerUrl response = burgerAPIService.post(HtmlUtil.getPhotoList(venueId, venueName));

        String latestBurgerUrl = response.getUrlWithBurger();

        return CompletableFuture.completedFuture(new ApiResponse(venueName, latestBurgerUrl));
    }
}
