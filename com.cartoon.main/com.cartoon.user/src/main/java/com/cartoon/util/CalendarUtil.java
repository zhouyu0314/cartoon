package com.cartoon.util;

import com.cartoon.exceptions.IllegalParamsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    public static Date add(String oldDate,Integer num){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currdate = null;
        try {
            currdate = format.parse(oldDate);
        } catch (ParseException e) {
            throw new IllegalParamsException("时间参数违法");
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);
        currdate = ca.getTime();
        return currdate;
    }
}
