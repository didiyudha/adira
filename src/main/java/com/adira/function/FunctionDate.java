package com.adira.function;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by didi-realtime on 05/11/16.
 */
public class FunctionDate {

    private static String dateFormat = "yyyy-MM-dd";

    public static Date stringToDate(String dateStr) throws ParseException {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Date date = df.parse(dateStr);
        return date;
    }

    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public static int getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        return month;
    }

    public static int getDateIndexFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int d = calendar.get(Calendar.DATE);
        return d;
    }
}
