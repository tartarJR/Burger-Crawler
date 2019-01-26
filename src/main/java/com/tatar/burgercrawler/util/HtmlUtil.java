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
import java.util.List;

public class HtmlUtil {

    private static final Logger log = LoggerFactory.getLogger(HtmlUtil.class);

    public static List<Photo> getPhotoList(String venueId) {

        List<Photo> photoList = new ArrayList<>();

        try {

            log.debug("VENUE URL: " + HtmlConstants.BASE_URL + venueId + HtmlConstants.PATH_PHOTOS);

            Document doc = Jsoup.connect(HtmlConstants.BASE_URL + venueId + HtmlConstants.PATH_PHOTOS).get();
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
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return photoList;
    }

}
