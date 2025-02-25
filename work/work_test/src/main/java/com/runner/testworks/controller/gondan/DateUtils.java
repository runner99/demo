package com.runner.testworks.controller.gondan;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author cq
 *
 */
@Slf4j
public class DateUtils {



    private static class Formats {
		
		// 完整时间
		private final DateFormat simple = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		// 年月日
		private final DateFormat dtSimple = new SimpleDateFormat("yyyy-MM-dd");

		private final DateFormat dtSimple2 = new SimpleDateFormat("yyyyMMdd");
		
		private final DateFormat simpleFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		
		private final DateFormat dtSimpleLong = new SimpleDateFormat("yyyyMMddHHmmss");

		private final DateFormat toHourFormat = new SimpleDateFormat("yyyyMMdd't'HH");

		
		// 年月日
		private final DateFormat dtSimpleChinese = new SimpleDateFormat(
				"yyyy年MM月dd日");
		
		// 年月日
		private final DateFormat dtChinese = new SimpleDateFormat(
				"yyyy年MM月dd日 HH点mm分");

	}


	private static final ThreadLocal<Formats> LOCAL = new ThreadLocal<Formats>();

	private static Formats getFormats() {
		Formats f = LOCAL.get();
		if (f == null) {
			f = new Formats();
			LOCAL.set(f);
		}
		return f;
	}

	
	private static Calendar getCal(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * 返回yyyy-MM-dd HH:mm:ss完整时间
	 * 
	 * @param date null取当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static final String simpleFormat(Date date) {
		if (date == null) {
			date=new Date();
		}

		return getFormats().simple.format(date);
	}


	/**
	 * 返回yyyy-MM-dd
	 * 
	 * @param date 
	 * 
	 * @return yyyy-MM-dd
	 */
	public static final String dtSimpleFormat(Date date) {
		if (date == null) {
			return getCurday();
		}

		return getFormats().dtSimple.format(date);
	}


	/**
	 * yyyy-MM-dd 日期字符转换为时间
	 * 
	 * @param stringDate  yyyy-MM-dd
	 * 
	 * @return date
	 * 
	 */
	public static final Date string2Date(String stringDate){
		
			if (stringDate == null||stringDate.equals("")) {
				return null;
			}
			try {
				return getFormats().dtSimple.parse(stringDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		
	}

	/**
	 * 返回日期时间
	 * 
	 * @param stringDate yyyy-MM-dd HH:mm:ss
	 * 
	 * @return date 日期
	 * 
	 * @throws ParseException 
	 */
	public static final Date string2DateTime(String stringDate)
			throws ParseException {
		if (stringDate == null||stringDate.equals("")) {
			return null;
		}

		return getFormats().simple.parse(stringDate);
	}
	
	
	/**
	 * 返回日期时间
	 * 
	 * @param stringDate yyyyMMddHHmmss
	 * 
	 * @return date 日期
	 * 
	 * @throws ParseException 
	 */
	public static final Date string2DateLong(String stringDate)
			throws ParseException {
		if (stringDate == null||stringDate.equals("")) {
			return null;
		}

		return getFormats().dtSimpleLong.parse(stringDate);
	}

	/**
	 * 时间转换字符串 2005-06-30 15:50
	 * 
	 * @param date 
	 * 
	 * @return yyyy-MM-dd HH:mm
	 */
	public static final String simpleDate(Date date) {
		if (date == null) {
			date=new Date();
		}

		return getFormats().simpleFormat.format(date);
	}
	
	/**
	 * yyyy年MM月dd日 日期字符转换为时间
	 * 
	 * @param stringDate 
	 * 
	 * @return Date  
	 * 
	 * @throws ParseException
	 */
	public static final Date chineseString2Date(String stringDate) {
		if (stringDate == null) {
			return null;
		}
		try {
			return getFormats().dtSimpleChinese.parse(stringDate);
		} catch (ParseException e) {
			return null;
		}
		
	}
	
	/**
	 * yyyy年MM月dd日 参数空为当前时间
	 * 
	 * @param date 
	 * 
	 * @return string
	 */
	public static final String dtSimpleChineseFormat(Date date) {
		if (date == null) {
			date=new Date();
		}
		return getFormats().dtSimpleChinese.format(date);
	}
	
	/**
	 * yyyy年MM月dd日 HH点MM分 参数空为当前时间
	 * 
	 * @param date 
	 * 
	 * @return string
	 */
	public static final String dtChineseFormat(Date date) {
		if (date == null) {
			date=new Date();
		}
		return getFormats().dtChinese.format(date);
	}
	

	/**
	 * 
	 * @return 当年
	 */
	public static String getYear() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	
	/**
	 * 
	 * @return 当前日期 yyyy-MM-dd
	 */
	public static String getCurday() {
		String datime = getFormats().dtSimple.format(new Date());
		return datime;
	}
	
	/**
	 * 
	 * @return 当前日期 yyyyMMdd
	 */
	public static String getCurday2() {
		String datime = getFormats().dtSimple2.format(new Date());
		return datime;
	}

	/**
	 *
	 * @return 当前日期 yyyyMMdd
	 */
	public static String getCurdayHour() {
		String datime = getFormats().toHourFormat.format(new Date());
		return datime;
	}
	
	/**
	 * date 转为 string
	 * @param date 
	 * @return yyyy-MM-dd
	 */
	public static String date2String(Date date) {
		String p = getFormats().dtSimple.format(date);
		return p;
	}

	/**
	 * date 转为yyyy-MM-dd HH:mm:ss
	 * @param date 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2String2(Date date) {
		String p = getFormats().simple.format(date);
		return p;
	}

	/**
	 * date 转为yyyyMMdd
	 * @param date
	 * @return yyyyMMdd
	 */
	public static String date2String3(Date date) {
		String p = getFormats().dtSimple2.format(date);
		return p;
	}

	public static String date2StringToHour(Date date) {
		String p = getFormats().toHourFormat.format(date);
		return p;
	}



	/**
	 * 加天数
	 * 
	 * @param date 
	 * @param x 增加的天数 可为负
	 * @return 增加x天后的date
	 */
	public static Date addDay(Date date,  int x) {
		Calendar cal = getCal(date);
		cal.add(Calendar.DAY_OF_MONTH,  x);
		Date date2 = cal.getTime();
		return date2;
	}

	/**
	 * 增加天数
	 * 
	 * @param time 时间字符串
	 * @param x 天数
	 * @return 增加x天后的date
	 * @throws ParseException 
	 */
	public static Date addDay(String time,  int x) throws ParseException {
		Date date = string2Date(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,  x);
		return cal.getTime();
	}
	
	
	/**
	 * 增加月数
	 * 
	 * @param time 时间字符串
	 * @param x 月数
	 * @return 增加x月后的date
	 * @throws ParseException 
	 */
	public static Date addMon(String time,  int x) throws ParseException {
		Date date = string2Date(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,  x);
		return cal.getTime();
	}


	/**
	 * 
	 * description : 得到日期所在周的下周一
	 * 
	 * @param date 日期
	 * @return 下周一
	 * 
	 * @author : cq
	 * 
	 */
	public static Date getNextMonday(String date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime((string2Date(date)));
		c.set(Calendar.DAY_OF_WEEK,  c.getFirstDayOfWeek()+6); // Sunday
		return addDay(c.getTime(), 1);
	}
	
	
	/**
	 * 
	 * description : 得到日期所在周的周n
	 * 
	 * @param date 日期
	 * @param n 周几
	 * @return Date
	 * 
	 * @author : cq
	 * 
	 */
	public static Date getWeekDay(String date, int n) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(string2Date(date));
		c.set(Calendar.DAY_OF_WEEK,  c.getFirstDayOfWeek()+n-1); 
		return c.getTime();
	}
	
	
	
	
	/**
	 * 返回日期所在的月 增加mon个月后的月份
	 * @param dt 指定date
	 * @param mon 增加的月数 可为负
	 * @return YYYY-MM
	 */
	public static final String getNextMon(String dt,  int mon) {
		if (dt == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime((string2Date(dt)));
		c.add(Calendar.MONDAY,  mon);
		String year = String.valueOf(c.get(Calendar.YEAR));
		int month = c.get(Calendar.MONTH) + 1;
		String sMonth;
		if (month < 10) {
			sMonth = "0" + String.valueOf(month);
		} else {
			sMonth = String.valueOf(month);
		}
		return year + "-" + sMonth;
	}
	
	/**
	 * 获取输入时间的月份的第一天
	 * 
	 * @param dt 
	 * @return string
	 */
	public static final Date getMonthFirstDay(String dt) {
		if (dt == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(string2Date(dt));
		c.set(Calendar.DAY_OF_MONTH,  1);
		return c.getTime();
	}
	
	/**
	 * 获取输入时间的月份的最后一天
	 * 
	 * @param dt 
	 * @return Date
	 */
	public static final Date getMonthLastDay(String dt) {
		if (dt == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(string2Date(dt));
		c.add(Calendar.MONTH,  1);
		c.set(Calendar.DAY_OF_MONTH,  1);
		return addDay(c.getTime(), -1);
	}
	
	/**
	 * 相差天数
	 * @param start Date
	 * @param endDate Date
	 * @return int
	 */
	public static int getBetweenDate(Date start, Date endDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		int end = c.get(Calendar.YEAR) * 365 + c.get(Calendar.DAY_OF_YEAR);
		c.setTime(start);
		return end - (c.get(Calendar.YEAR) * 365 + c.get(Calendar.DAY_OF_YEAR));
	}
	
	/**
	 * 取与当前时间相差n天的时间yyyy-MM-dd HH:mm:ss
	 * @param n 负:向前   正:向后 	0:当前时间
	 * @return str 
	 */
	public static String getTimeBetweenNow(Integer n){
		Date cur=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(cur);
		
		c.add(Calendar.DAY_OF_MONTH, n);
		String oneDay =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return oneDay;
	}
	
	
	
	
	
	
	
	/**
	 * 取上个月的第一天或最后一天
	 * 
	 * @param flag
	 *            false：第一天；true：最后一天
	 * @return
	 */
	public static String getDayByMonth(boolean flag) {
		Date cur=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(cur);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String oneDay="";
		
		if (flag){
			c.add(Calendar.DAY_OF_MONTH, -1);
			oneDay =new SimpleDateFormat("yyyy-MM-dd").format(c.getTime())+" 23:59:59";
		}
		else{
			c.add(Calendar.MONDAY, -1);
			oneDay =new SimpleDateFormat("yyyy-MM-dd").format(c.getTime())+" 00:00:00";
		}
		return oneDay;
	}

	
	/**
	 * 与输入时间相差num分钟的时间   正数向后 负数向前
	 * @param time 
	 * @param num 
	 * @return Date 
	 */
	public static Date addMinuteDate(String time, int num){
		Date date=null;
		try {
			date = string2DateTime(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE,  num);
		return cal.getTime();
	}
	
	/**
	 * 与输入时间相差num分钟的时间    正数向后 负数向前
	 * @param time 
	 * @param num 
	 * @return str yyyy-MM-dd HH:mm:ss 
	 */
	public static String addMinuteStr(String time, int num){
		return date2String2(addMinuteDate(time, num));
	}
	
	/**
	 * 与输入时间相差seconds秒的时间   正数向后 负数向前
	 * @param date 
	 * @param seconds 
	 * @return Date
	 */
	public static Date addSecond(Date date, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND,  seconds);
		return cal.getTime();
	}

	public static Date minZeroing(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE,0);//设为当前分钟为0
		cal.set(Calendar.SECOND,0);//设为当前秒为0
		Date v = cal.getTime();
		return v;
	}
	
	/**
	 * 
	 * description : 得到本周的周n
	 * 
	 * @param n 周几
	 * @return Date
	 * 
	 * @author : cq
	 * 
	 */
	public static String getCurWeekDay(int n) {
		return getCurWeekDay(n, null);
	}

	public static String getCurWeekDay(int n, Date date) {
		Calendar c = Calendar.getInstance();
		if(date != null) {
			c.setTime(date);
		}
		if(n%7==0){//周日
			if(c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				c.set(Calendar.DAY_OF_WEEK,  c.getFirstDayOfWeek());
				c.add(Calendar.DATE, 7);
			}
		}else{
			c.set(Calendar.DAY_OF_WEEK,  c.getFirstDayOfWeek()+n); 
		}
		
		
		return dtSimpleFormat(c.getTime());
	}
	
	
	/**
	 * 比较时间  是否 small早于big
	 * @param small yyyy-mm-dd hh:mm
	 * @param big yyyy-mm-dd hh:mm
	 * @return true small<big  else false 
	 */
	public static boolean isBefore(String small,String big){
		boolean r=false;
		try {
			Date date1=getFormats().simpleFormat.parse(small);
			Date date2=getFormats().simpleFormat.parse(big);
			if(date1.before(date2)){
				r=true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}

	public static Date getTodayBeginTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getOneDayBeginTime(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 把时间调整为指定date+day的0点0分0秒
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static long increaseDayZero(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		c.add(Calendar.DAY_OF_MONTH, day);
		
		return setZero(c.getTime());
		
	}
	
	/**
	 * 把时间调整为当前天数的0点0分0秒
	 *
	 * @param date
	 * @return
	 */
	
	public static long setZero(Date date) {
		if (date == null) {
			return 0l;
		}
		try {
			Calendar calendar = Calendar.getInstance();
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
	
	/**
	 * 测试
	 * @param args 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(getOneDayBeginTime(addDay(new Date(),-1)));
		System.out.println(System.currentTimeMillis());
		System.out.println(string2DateTime("2021-01-04 16:48:57").getTime());

	}




}