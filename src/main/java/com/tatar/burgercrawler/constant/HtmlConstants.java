package com.tatar.burgercrawler.constant;

public class HtmlConstants {

    public static final String BASE_URL = "https://tr.foursquare.com/v/";
    public static final String PATH_PHOTOS = "/photos";
    public static final String TARGET_IMAGES = "div.photosBlock div.photo:lt(10)";
    public static final String TARGET_PHOTO_CLASS = "mainPhoto";
    public static final String TARGET_PHOTO_SRC = "src";
    public static final String TARGET_META_CLASS = "meta";
    public static final String TARGET_DATE_CLASS = "date";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    public static final int TIME_OUT = 720 * 1000;
}
