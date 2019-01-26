package com.tatar.burgercrawler.util;

import com.tatar.burgercrawler.constant.DateConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {
        throw new UnsupportedOperationException();
    }


    public static Date convertDateStringToDate(String dateString) {

        DateFormat format = new SimpleDateFormat(DateConstants.DATE_FORMAT, Locale.ENGLISH);
        Date date = null;

        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            //log.error(e.getMessage(), e);
            date = convertStringToDate(dateString);
        }

        return date;
    }

    // Converts a sentence(e.g. "4 days ago") to Java Date.
    private static Date convertStringToDate(String dateString) {

        String[] dateStringArray = dateString.split(DateConstants.SPLIT_REGEX);

        String timeUnit = dateStringArray[1]; // days, weeks or months
        int amountOfTime = Integer.parseInt(dateStringArray[0]); // how many days, weeks or months

        Date date = null;

        switch (timeUnit) {
            case DateConstants.DAY:
                date = new Date(System.currentTimeMillis() - (amountOfTime * DateConstants.DAY_IN_MS));

                break;
            case DateConstants.WEEK: {
                int noOfDays = amountOfTime * DateConstants.DAYS_IN_WEEK;
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -noOfDays);
                date = calendar.getTime();

                break;
            }
            case DateConstants.MONTH: {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, -amountOfTime);
                date = cal.getTime();

                break;
            }
        }

        DateFormat format = new SimpleDateFormat(DateConstants.DATE_FORMAT, Locale.ENGLISH);

        try {
            String formattedDateString = format.format(date);
            date = format.parse(formattedDateString);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }

        return date;
    }

}
