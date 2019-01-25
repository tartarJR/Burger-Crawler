package com.tatar.burgercrawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static Date convertStringToDate(String dateString) {

        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = null;

        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }

        return date;
    }

}
