package com.tatar.burgercrawler.util;

import com.tatar.burgercrawler.constant.HtmlConstants;
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

    public static List<String> getPhotoUrlList(String venueId) {

        List<String> photoUrlList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(HtmlConstants.BASE_URL + venueId + HtmlConstants.PATH_PHOTOS).get();
            Elements photosBlock = doc.select(HtmlConstants.TARGET_DIV);
            Elements photos = photosBlock.first().children();

            for (Element photo : photos) {
                Element targetPhoto = photo.getElementsByClass(HtmlConstants.TARGET_PHOTO_CLASS).first();

                if (targetPhoto != null) {
                    String photoUrl = targetPhoto.absUrl(HtmlConstants.TARGET_PHOTO_SRC);
                    photoUrlList.add(photoUrl);
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return photoUrlList;
    }

}
