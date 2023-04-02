package com.example.pethospitalbackend.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateUtil {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getDate(String dateString) {
        try {
            return new Date(formatter.parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateString(Date date) {
        return formatter.format(date);
    }
}
