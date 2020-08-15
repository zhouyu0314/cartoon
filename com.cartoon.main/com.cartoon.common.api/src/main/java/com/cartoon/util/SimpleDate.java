package com.cartoon.util;

import com.cartoon.exceptions.IllegalParamsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDate {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    public static String getDate(Date date) throws IllegalParamsException{
        String format = null;
        try {
            format = sdf.format(date);
        } catch (Exception e) {
            throw new IllegalParamsException("时间格式不匹配");
        }
        return format;
    }


    public static String getDate1(Date date) throws IllegalParamsException{
        String format = null;
        try {
            format = sdf1.format(date);
        } catch (Exception e) {
            throw new IllegalParamsException("时间格式不匹配");
        }
        return format;
    }

    public static Date parseDate(String date){
        Date time = null;
        try {
            time = sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalParamsException("时间格式不匹配");
        }

        return time;
    }
}
