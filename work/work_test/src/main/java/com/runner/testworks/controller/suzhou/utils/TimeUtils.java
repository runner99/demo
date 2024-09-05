/**
 * 版权所有：美创科技
 * 项目名称：
 * 版本号：
 * 创建者：hongyuan
 * 创建日期：2019/4/11
 * 文件说明：
 * 最近修改者：
 * 最近修改日期：
 */
package com.runner.testworks.controller.suzhou.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 类说明：时间工具类
 *
 * @author hy
 */
@Slf4j
public class TimeUtils {

    public static final long DAY_TIME_STAMP = 24 * 60 * 60 * 1000L;

    public static final long HOUR_TIME_STAMP = 60 * 60 * 1000L;

    public static final long MINUTE_TIME_STAMP = 60000L;

    public static final long DAY_HOUR = 24L;

    private static final String TIME_ZONE = "GMT+8";

    /**
     * 获取七天前0点0分0秒
     */
    public static long getBeforeSeven(Long timeStamp) {
        try {
            return setZero(new Date(timeStamp - DAY_TIME_STAMP * 6));
        } catch (Exception ex) {

        }
        return 0L;
    }

    /**
     * 根据时间戳获取指定格式的字符串
     *
     * @param timeStamp
     * @param formatEnum
     * @return
     */
    public static String getStrByTime(Long timeStamp, TimeFormatEnum formatEnum) {
        try {
            return getStrByTime(timeStamp,formatEnum.get());
        } catch (Exception ex) {
//            log.error(ExceptionUtils.getFullStackTrace(ex));
            return null;
        }
    }

    /**
     * 根据时间戳获取指定格式的字符串
     *
     * @param timeStamp
     * @param format
     * @return
     */
    public static String getStrByTime(Long timeStamp, DateFormat format) {
        if (timeStamp == null || timeStamp <= 0 || format == null) {
            return null;
        }

        try {
            return format.format(new Date(timeStamp));
        } catch (Exception ex) {
//            log.error(ExceptionUtils.getFullStackTrace(ex));
            return null;
        }
    }

    public static void main(String[] args) {
        long beforeSeven = getBeforeSeven(System.currentTimeMillis());
        System.out.println(beforeSeven);
    }
    /**
     * 把时间调整为当前天数的0点0分0秒
     *
     * @param date
     * @return
     */

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
//            log.error(ExceptionUtils.getFullStackTrace(ex));
            return 0;
        }
    }
}
