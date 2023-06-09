package com.runner99.minus;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author weizhenqiang
 * @date 2023/5/10 10:32
 */
public class MinusBug {

    private static final String TIME_ZONE = "GMT+8";
    public static void main(String[] args) {
        long l = increaseDayZero(new Date(), 0);
        System.out.println(l);

    }
    public static long increaseDayZero(Date date,int day) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
        c.setTime(date);

        c.add(Calendar.DAY_OF_MONTH, day);

        return setZero(c.getTime());

    }
    public static long setZero(Date date) {
        if (date == null) {
            date = new Date();
        }
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime().getTime();
        } catch (Exception ex) {
            return 0;
        }
    }
}
