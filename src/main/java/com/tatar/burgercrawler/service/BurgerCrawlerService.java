package com.tatar.burgercrawler.service;

import com.tatar.burgercrawler.model.ApiResponse;
import com.tatar.burgercrawler.model.Photo;
import com.tatar.burgercrawler.model.BurgerUrl;
import com.tatar.burgercrawler.util.HtmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    private final BurgerAPIService burgerAPIService;

    public PhotoService(BurgerAPIService burgerAPIService) {
        this.burgerAPIService = burgerAPIService;
    }

    @Async("processExecutor")
    public CompletableFuture<ApiResponse> getPhotoList(String venueId, String venueName) {

        List<Photo> photoList = HtmlUtil.getPhotoList(venueId, venueName);

        List<String> photoUrlList = photoList.stream()
                .map(Photo::getUrl)
                .collect(Collectors.toList());

        BurgerUrl response = burgerAPIService.post(photoUrlList);

        if (response != null) {
            logger.info("RESPONSE URL: " + response.getUrlWithBurger());
        }

        return CompletableFuture.completedFuture(new ApiResponse(venueName, response.getUrlWithBurger()));
    }
}
