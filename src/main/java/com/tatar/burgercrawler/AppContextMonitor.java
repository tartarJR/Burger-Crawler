package com.tatar.burgercrawler;

import com.tatar.burgercrawler.service.BurgerService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppContextMonitor implements ApplicationListener<ApplicationEvent> {

    private final BurgerService burgerService;

    public AppContextMonitor(BurgerService burgerService) {
        this.burgerService = burgerService;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            burgerService.getBurgerVenues(null);
        }
    }
}
