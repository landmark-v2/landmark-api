package com.landmark.app.utils;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StaticHelper {

    public static Date intToDate(BigInteger dateTime, String format) {
        Date date = new Date();

        try {
            String stringTime = String.valueOf(dateTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(stringTime);
        } catch (Exception e) {
        }

        return date;
    }

    public static Date stringToDate(String dateTime, String format) {
        try {
            String stringTime = String.valueOf(dateTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(stringTime);
        } catch (Exception e) {
            return null;
        }
    }

    public static BigInteger dateToBigInteger(Date dateTime, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String stringTime = sdf.format(dateTime);
            return new BigInteger(stringTime);
        } catch (Exception e) {
            return new BigInteger("0");
        }
    }

    public static String encodeUTF8(String keyword) {
        return URLEncoder.encode(keyword);
    }

    public static String getInfoValue(Object info, String defaultValue) {
        return info != null ? info.toString() : defaultValue;
    }

}
