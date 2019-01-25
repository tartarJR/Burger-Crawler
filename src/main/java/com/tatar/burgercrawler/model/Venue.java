package com.tatar.burgercrawler.model;

import java.util.List;

public class Venue {

    private String id;
    private String name;
    private List<String> photoUrlList;

    public Venue() {
    }

    public Venue(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Venue(String id, String name, List<String> photoUrlList) {
        this.id = id;
        this.name = name;
        this.photoUrlList = photoUrlList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrlList() {
        return photoUrlList;
    }

    public void setPhotoUrlList(List<String> photoUrlList) {
        this.photoUrlList = photoUrlList;
    }
}
