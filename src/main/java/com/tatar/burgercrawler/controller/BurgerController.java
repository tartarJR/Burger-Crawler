package com.tatar.burgercrawler.controller;

import com.tatar.burgercrawler.model.ApiResponse;
import com.tatar.burgercrawler.service.BurgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BurgerController {

    private static final Logger logger = LoggerFactory.getLogger(BurgerController.class);

    private final BurgerService burgerService;

    public BurgerController(BurgerService burgerService) {
        this.burgerService = burgerService;
    }

    @GetMapping("/venues/burger-venues")
    public List<ApiResponse> getBurgerVenues(@RequestParam(value = "offset", required = false) String offset) {

        logger.info("request received on getBurgerVenues..");

        return burgerService.getBurgerVenues(offset);
    }

}
