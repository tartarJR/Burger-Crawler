package com.tatar.burgercrawler.model;

public class Photo {

    private String url;
    private String date;

    public Photo() {
    }

    public Photo(String url, String date) {
        this.url = url;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
