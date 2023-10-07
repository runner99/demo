package com.runner.testworks.controller.suzhou.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 时间格式枚举类
 */
public enum TimeFormatEnum {
    /**
     * 年-月-日 时:分:秒
     */
    formatter("yyyy-MM-dd HH:mm:ss"),
    /**
     *年-月-日
     */
    dateFormat("yyyy-MM-dd"),
    /**
     *年-月
     */
    monthFormat("yyyy-MM"),
    /**
     *年月日
     */
    dateFormat2("yyyyMMdd"),
    /**
     *年/月/日
     */
    dateFormat3("yyyy/MM/dd"),
    /**
     *年-月-日 时
     */
    dateHourFormat("yyyy-MM-dd HH"),
    /**
     *时分秒
     */
    hourFormat("HHmmss"),
    /**
     *时:分:秒
     */
    hourFormat2("HH:mm:ss"),
    /**
     *时:分
     */
    hourFormat4("HH:mm"),
    /**
     *年：月：日：时
     */
    hourFormat6("yyyyMMdd-HH"),
    /**
     *时
     */
    hourFormat3("HH")
    ;

    private String str;

    private TimeFormatEnum(String str) {
        this.str = str;
    }

    /**
     * 每次调用都需要初始化 避免DateFormat线程安全问题
     *
     * @return
     */
    public DateFormat get() {
        DateFormat format = new SimpleDateFormat(str);
        return format;
    }

}
