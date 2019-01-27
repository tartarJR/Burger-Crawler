package com.tatar.burgercrawler.model;

import java.io.Serializable;
import java.util.List;

public class Venue implements Serializable {

    private String id;
    private String name;
    private List<Photo> photoList;

    public Venue() {
    }

    public Venue(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Venue(String id, String name, List<Photo> photoList) {
        this.id = id;
        this.name = name;
        this.photoList = photoList;
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

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }
}
