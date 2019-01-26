package com.tatar.burgercrawler.service;

import com.tatar.burgercrawler.model.Photo;
import com.tatar.burgercrawler.util.HtmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PhotoService {

    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    @Async("processExecutor")
    public CompletableFuture<List<Photo>> getPhotoList(String venueId, String venueName) {
        return CompletableFuture.completedFuture(HtmlUtil.getPhotoList(venueId, venueName));
    }
}
