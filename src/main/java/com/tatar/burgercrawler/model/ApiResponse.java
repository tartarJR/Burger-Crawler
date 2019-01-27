package com.tatar.burgercrawler.model;

public class ApiResponse {

    private String venueName;
    private String latestBurgerPhotoUrl;

    public ApiResponse() {
    }

    public ApiResponse(String venueName, String latestBurgerPhotoUrl) {
        this.venueName = venueName;
        this.latestBurgerPhotoUrl = latestBurgerPhotoUrl;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getlatestBurgerPhotoUrl() {
        return latestBurgerPhotoUrl;
    }

    public void setlatestBurgerPhotoUrl(String latestBurgerPhotoUrl) {
        this.latestBurgerPhotoUrl = latestBurgerPhotoUrl;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "venueName='" + venueName + '\'' +
                ", latestBurgerPhotoUrl='" + latestBurgerPhotoUrl + '\'' +
                '}';
    }
}
