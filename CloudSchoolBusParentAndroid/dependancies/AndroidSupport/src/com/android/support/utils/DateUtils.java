package com.android.support.utils;

import com.android.support.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    /**
     * 返回一定格式的当前时间
     *
     * @param pattern "yyyy-MM-dd HH:mm:ss E"
     * @return
     */
    public static String getCurrentDate(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(System.currentTimeMillis());
        String dateString = simpleDateFormat.format(date);
        return dateString;

    }

    public static long getDateMillis(String dateString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        long millionSeconds = 0;
        try {
            millionSeconds = sdf.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒

        return millionSeconds;
    }

    public static String currentMillisString() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        return ts;
    }

    /**
     * 格式化输入的millis
     *
     * @param millis
     * @param pattern yyyy-MM-dd HH:mm:ss E
     * @return
     */
    public static String dateFormat(long millis, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Hongkong"));
        Date date = new Date(millis);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    /**
     * 将dateString原来old格式转换成new格式
     *
     * @param dateString
     * @param oldPattern yyyy-MM-dd HH:mm:ss E
     * @param newPattern
     * @return oldPattern和dateString形式不一样直接返回dateString
     */
    public static String dateFormat(String dateString, String oldPattern, String newPattern) {
        long millis = getDateMillis(dateString, oldPattern);
        if (0 == millis) {
            return dateString;
        }
        String date = dateFormat(millis, newPattern);
        return date;
    }

    public static String timelineTimestamp(String timestamp) {
        String retString = "";
        SimpleDateFormat toYearSdf = new SimpleDateFormat("MM-dd HH:mm");

        if (timestamp != null) {
            long foo = Long.parseLong(timestamp) * 1000;
            long tmp = System.currentTimeMillis() - foo;
            if (tmp < 12 * 60 * 60 * 1000) {
                if (tmp < 60 * 60 * 1000) {
                    if (tmp <= 60 * 1000) {
                        retString = "1" + "Minutes ago";
                    } else {
                        retString = (tmp / (60 * 1000) + "Minutes ago");
                    }
                } else {
                    retString = (tmp / (60 * 60 * 1000) + "Hours ago");
                }
            } else {
                retString = toYearSdf.format(new Date(foo));
            }
        }

        return retString;
    }

}