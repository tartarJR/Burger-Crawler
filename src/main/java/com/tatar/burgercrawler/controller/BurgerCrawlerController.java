package com.tatar.burgercrawler.controller;

import com.tatar.burgercrawler.model.ApiResponse;
import com.tatar.burgercrawler.model.Venue;
import com.tatar.burgercrawler.service.BurgerCrawlerService;
import com.tatar.burgercrawler.service.VenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class BurgerCrawlerController {

    private static final Logger logger = LoggerFactory.getLogger(BurgerCrawlerController.class);

    private final VenueService venueService;
    private final BurgerCrawlerService burgerCrawlerService;

    public BurgerCrawlerController(VenueService venueService, BurgerCrawlerService burgerCrawlerService) {
        this.venueService = venueService;
        this.burgerCrawlerService = burgerCrawlerService;
    }

    @GetMapping("/venues/burger-venues")
    public List<ApiResponse> getBurgerVenues(@RequestParam(value = "offset", required = false) String offset) {

        long startTime = System.nanoTime();

        List<Venue> venues = venueService.getVenues(offset);

        List<CompletableFuture<ApiResponse>> completableFutures = new ArrayList<>();

        for (Venue venue : venues) {
            CompletableFuture<ApiResponse> responseFuture = burgerCrawlerService.getPhotoList(venue.getId(), venue.getName());
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
