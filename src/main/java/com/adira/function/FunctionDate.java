package com.adira.function;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
