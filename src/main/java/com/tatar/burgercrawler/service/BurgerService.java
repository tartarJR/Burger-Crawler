package com.tatar.burgercrawler.service;

import com.tatar.burgercrawler.model.ApiResponse;
import com.tatar.burgercrawler.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@CacheConfig(cacheNames = {"venues"})
public class BurgerService {

    private static final Logger logger = LoggerFactory.getLogger(BurgerService.class);

    private final FsAPIService fsAPIService;
    private final CrawlerService crawlerService;

    public BurgerService(FsAPIService fsAPIService, CrawlerService crawlerService) {
        this.fsAPIService = fsAPIService;
        this.crawlerService = crawlerService;
    }

    @Cacheable
    public List<ApiResponse> getBurgerVenues(String offset) {
        long startTime = System.nanoTime();

        List<Venue> venues = fsAPIService.getVenues(offset);

        List<CompletableFuture<ApiResponse>> completableFutures = new ArrayList<>();

        for (Venue venue : venues) {
            CompletableFuture<ApiResponse> responseFuture = crawlerService.getPhotoList(venue.getId(), venue.getName());
            completableFutures.add(responseFuture);
        }

        // wait until they are all done
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

        List<ApiResponse> burgerVenues = new ArrayList<>();

        try {
            for (CompletableFuture<ApiResponse> responseFuture : completableFutures) {
                if (!responseFuture.get().getlatestBurgerPhotoUrl().equals("")) {
                    burgerVenues.add(responseFuture.get());
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        logger.info("Execution time in seconds : " + timeElapsed / 1000000000);

        return burgerVenues;
    }
}
