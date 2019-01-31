package com.tatar.burgercrawler.util;

import com.tatar.burgercrawler.constant.DateConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Takes a date String transforms it to Java Date of given date format
     *
     * @param dateString A date string.
     * @return Java Date transformed from the dateString
     */
    public static Date convertDateStringToDate(String dateString) {

        DateFormat format = new SimpleDateFormat(DateConstants.DATE_FORMAT, new Locale("tr")); // TODO find a solution for dynamic localization
        Date date;

        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            date = convertStringToDate(dateString);
        }

        return date;
    }

    /**
     * Converts a sentence(e.g. "4 days ago", "February 3") to Java Date.
     * This method need a bir refactoring tho, Need a better way to do this
     *
     * @param dateString A date string.
     * @return Java Date transformed from the sentence
     */
    private static Date convertStringToDate(String dateString) {

        String[] dateStringArray = dateString.split(DateConstants.SPLIT_REGEX);

        Date date = null;

        if (dateStringArray.length == 3) {

            String timeUnit = dateStringArray[1]; // days, weeks or months
            int amountOfTime = Integer.parseInt(dateStringArray[0]); // how many days, weeks or months

            switch (timeUnit) {
                case DateConstants.DAY:
                    date = new Date(System.currentTimeMillis() - (amountOfTime * DateConstants.DAY_IN_MS));

                    break;
                case DateConstants.WEEK: {
                    int noOfDays = amountOfTime * DateConstants.DAYS_IN_WEEK;
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_YEAR, -noOfDays);
                    date = cal.getTime();

                    break;
                }
                case DateConstants.MONTH: {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MONTH, -amountOfTime);
                    date = cal.getTime();

                    break;
                }
            }
        } else if (dateStringArray.length == 2) {
            String monthName = dateStringArray[0];
            int day = Integer.parseInt(dateStringArray[1]);

            Date month = null;

            try {
                month = new SimpleDateFormat("MMM", new Locale("tr")).parse(monthName);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar cal = Calendar.getInstance();

            if (month != null) {
                cal.setTime(month);
            }

            int monthNumber = cal.get(Calendar.MONTH);

            cal.set(Year.now().getValue(), monthNumber, day, 0, 0);

            date = cal.getTime();
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
