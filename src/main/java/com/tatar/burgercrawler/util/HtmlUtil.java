package com.tatar.burgercrawler.util;

import com.tatar.burgercrawler.constant.HtmlConstants;
import com.tatar.burgercrawler.model.Photo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HtmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(HtmlUtil.class);

    private HtmlUtil() {
        throw new UnsupportedOperationException();
    }

    public static List<Photo> getPhotoList(String venueId, String venueName) {

        List<Photo> photoList = new ArrayList<>();

        try {

            logger.info("VENUE URL: " + HtmlConstants.BASE_URL + convertNameToSlug(venueName) + venueId + HtmlConstants.PATH_PHOTOS);

            Document doc = Jsoup.connect(HtmlConstants.BASE_URL + convertNameToSlug(venueName) + venueId + HtmlConstants.PATH_PHOTOS).timeout(120 * 1000).get();
            Elements photosBlockDiv = doc.select(HtmlConstants.TARGET_DIV);
            Elements images = photosBlockDiv.first().children();

            for (Element image : images.subList(0, Math.min(30, images.size()))) {
                Element targetPhoto = image.getElementsByClass(HtmlConstants.TARGET_PHOTO_CLASS).first();
                Element targetMetaDiv = image.getElementsByClass(HtmlConstants.TARGET_META_CLASS).first();

                String photoUrl = null;
                String photoDate = null;

                if (targetPhoto != null) {
                    photoUrl = targetPhoto.absUrl(HtmlConstants.TARGET_PHOTO_SRC);
                }

                if (targetMetaDiv != null) {
                    photoDate = targetMetaDiv.getElementsByClass(HtmlConstants.TARGET_DATE_CLASS).last().text();
                }

                Photo photo = new Photo(photoUrl, DateUtil.convertDateStringToDate(photoDate));

                photoList.add(photo);

                Collections.sort(photoList);
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return photoList;
    }

    private static String convertNameToSlug(String venueName) {

        String[] nameWords = venueName.toLowerCase().split(" ");

        String slug;

        if (nameWords.length == 0) {
            slug = venueName.toLowerCase();
        } else {
            StringBuilder slugBuilder = new StringBuilder();

            for (int i = 0; i < nameWords.length; i++) {

                nameWords[i] = nameWords[i].replaceAll("[()']", "");

                if (i + 1 != nameWords.length) {
                    if (nameWords[i].equals("&")) {
                        slugBuilder.append("-");
                    } else {
                        slugBuilder.append(nameWords[i]).append("-");
                    }
                } else {
                    slugBuilder.append(nameWords[i]);
                }
            }

            slug = slugBuilder.toString();
        }

        return slug + "/";
    }

}
