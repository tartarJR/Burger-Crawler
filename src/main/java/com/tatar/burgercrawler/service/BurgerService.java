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

    // constructor injection
    public BurgerService(FsAPIService fsAPIService, CrawlerService crawlerService) {
        this.fsAPIService = fsAPIService;
        this.crawlerService = crawlerService;
    }

    /**
     * Integrates other 3 services.
     * Gets venues that categorized as restaurants and retrieves their Foursquare ids along with names.
     * Crawls each venue's Foursquare page for photos and their upload dates.
     * Sorts images by upload dates because ML API replies with the first burger image found
     * Sends sorted images to ML API and gets the response.
     * Called on app start up and the result is cached
     * Returns the burger venues with venue name and latest burger URL(the list of ApiResponse)
     *
     * @param offset value for pagination. To be sent to Foursquare API, not required.
     * @return the image URL list of the given venue
     */
    @Cacheable
    public List<ApiResponse> getBurgerVenues(String offset) {

        logger.info("retrieving venues from Foursquare API..");

        // retrieve venues from FourSquare API
        List<Venue> venues = fsAPIService.getVenues(offset);

        logger.info("crawling for photos..");

        // list of completableFutures. Each async process of CrawlerService will be stored here
        List<CompletableFuture<ApiResponse>> completableFutures = new ArrayList<>();

        // get photos for each venue asynchronously
        for (Venue venue : venues) {
            CompletableFuture<ApiResponse> responseFuture = crawlerService.getPhotoList(venue.getId(), venue.getName());
            completableFutures.add(responseFuture);
        }

        // wait until they are all done
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

        logger.info("retrieving burger venues..");
        List<ApiResponse> burgerVenues = new ArrayList<>();

        // get each response from future object and return them as a list of ApiResponse object
        try {
            for (CompletableFuture<ApiResponse> responseFuture : completableFutures) {
                if (!responseFuture.get().getlatestBurgerPhotoUrl().equals("")) {
                    burgerVenues.add(responseFuture.get());
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("received burger venues..");

        return burgerVenues;
    }
}
