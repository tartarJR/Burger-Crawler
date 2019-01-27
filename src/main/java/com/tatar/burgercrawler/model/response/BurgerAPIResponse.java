package com.tatar.burgercrawler.model.response;

import java.io.Serializable;

public class BurgerAPIResponse implements Serializable {

    private String urlWithBurger;

    public String getUrlWithBurger() {
        return urlWithBurger;
    }

    public void setUrlWithBurger(String urlWithBurger) {
        this.urlWithBurger = urlWithBurger;
    }
}
