package com.landmark.app.utils.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StaticHelper {

    public static Date stringToDate(String dateTime, String format) {
        try {
            String stringTime = String.valueOf(dateTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(stringTime);
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToString(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static int getCertNum() {
        try {
            // 6자리 인증 코드 생성
            int rand = (int) (Math.random() * 899999) + 100000;
            return rand;
        } catch (Exception e) {
            return 0;
        }
    }

}
