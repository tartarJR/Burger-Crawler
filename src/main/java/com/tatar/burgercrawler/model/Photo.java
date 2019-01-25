package com.tatar.burgercrawler.model;

import java.util.Date;

public class Photo {

    private String url;
    private Date date;

    public Photo() {
    }

    public Photo(String url, Date date) {
        this.url = url;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
