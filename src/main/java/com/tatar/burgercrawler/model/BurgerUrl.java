package com.tatar.burgercrawler.model;

import java.io.Serializable;

public class BurgerUrl implements Serializable {

    private String urlWithBurger;

    public BurgerUrl() {
    }

    public BurgerUrl(String urlWithBurger) {
        this.urlWithBurger = urlWithBurger;
    }

    public String getUrlWithBurger() {
        return urlWithBurger;
    }

    public void setUrlWithBurger(String urlWithBurger) {
        this.urlWithBurger = urlWithBurger;
    }
}
