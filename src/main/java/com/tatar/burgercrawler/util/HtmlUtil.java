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
import java.util.stream.Collectors;

public final class HtmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(HtmlUtil.class);

    private HtmlUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the list of image URLs for the given venue URL.
     *
     * @param venueId   The venue’s  Foursquare id.
     * @param venueName The venue’s  Foursquare name.
     * @return the image URL list of the given venue
     */
    public static List<String> getPhotoUrlList(String venueId, String venueName) {

        List<Photo> photoList = new ArrayList<>();

        try {

            // get the html page
            Document doc = Jsoup.connect(getVenueUrl(venueId, venueName))
                    .userAgent(HtmlConstants.USER_AGENT)
                    .timeout(HtmlConstants.TIME_OUT)
                    .ignoreHttpErrors(true)
                    .get();

            // get image elements from the html page
            Elements images = doc.select(HtmlConstants.TARGET_IMAGES);

            // get image URL and date for each image element
            for (Element image : images) {
                Element targetPhoto = image.getElementsByClass(HtmlConstants.TARGET_PHOTO_CLASS).first();
                Element targetMetaDiv = image.getElementsByClass(HtmlConstants.TARGET_META_CLASS).first();

                String photoUrl = targetPhoto.absUrl(HtmlConstants.TARGET_PHOTO_SRC);
                String photoDate = targetMetaDiv.getElementsByClass(HtmlConstants.TARGET_DATE_CLASS).last().text();

                Photo photo = new Photo(photoUrl, DateUtil.convertDateStringToDate(photoDate));

                photoList.add(photo);

            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        /*
         sort Photo objects based on date field
         ML API replies with the first burger image found
        */
        Collections.sort(photoList);

        // return only image URLs
        return photoList.stream()
                .map(Photo::getUrl)
                .collect(Collectors.toList());
    }

    /**
     * Returns the venue URL to be crawled.
     *
     * @param venueId   The venue’s  Foursquare id.
     * @param venueName The venue’s  Foursquare name.
     * @return the Foursquare URL of the venue
     */
    private static String getVenueUrl(String venueId, String venueName) {

        String[] nameWords = venueName.toLowerCase().split(" ");

        String slug;

        if (nameWords.length == 0) {
            slug = venueName.toLowerCase();
        } else {
            // split the venue name and remove unused characters for the URL creation
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

        return HtmlConstants.BASE_URL + slug + "/" + venueId + HtmlConstants.PATH_PHOTOS;
    }

}
