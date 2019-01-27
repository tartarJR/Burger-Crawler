package com.tatar.burgercrawler.model;

public class ApiResponse {

    private String venueName;
    private String latestBurgerUrl;

    public ApiResponse() {
    }

    public ApiResponse(String venueName, String latestBurgerUrl) {
        this.venueName = venueName;
        this.latestBurgerUrl = latestBurgerUrl;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getLatestBurgerUrl() {
        return latestBurgerUrl;
    }

    public void setLatestBurgerUrl(String latestBurgerUrl) {
        this.latestBurgerUrl = latestBurgerUrl;
    }
}
