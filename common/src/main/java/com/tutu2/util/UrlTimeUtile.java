package com.tutu2.util;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * url时间处理
 */
public class UrlTimeUtile {
	
	/**毫秒/分钟 换算单位*/
	public static final long msecOfHour = 3600000;
	
	/**默认国家授时中心地址*/
	public static final String webDateTimeUrl = "http://www.time.ac.cn/stime.asp";
	
	/**
	 * 获取网络时间
	 * @param webUrl
	 * @return
	 */
	public static LocalDateTime getLocalDateTime(String webUrl){
		if(webUrl == null || webUrl.replaceAll("\\s*", "").isEmpty()) {
			webUrl = webDateTimeUrl;
		}
		LocalDateTime localDateTime = null;
		try {
			URL t = new URL(webUrl);
			URLConnection uc = t.openConnection();
			uc.connect();
			long id = uc.getDate();
			Date d = new Date(id);
		    Instant instant = d.toInstant();
		    ZoneId zone = ZoneId.systemDefault();
		    localDateTime = LocalDateTime.ofInstant(instant, zone);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return localDateTime;
	}
	
	/**
	 * Date 转 LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime UDateToLocalDateTime(Date date) {
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    return localDateTime;
	}
	
	/**
	 * LocalDate 转 Date
	 * @return
	 */
	public static Date ULocalDateTimeToDate(LocalDateTime localDateTime) {
		 ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    Date date = Date.from(instant);
	    return date;
	}

	/**
	 * 获取指定天数后的日期
	 * @param dateNum 可选正负
	 * @return
	 */
	public static LocalDateTime UDateToLocalDateTime(LocalDateTime date,Integer dayNum) {
		final long msecOfDay = 86400000;
		long msecSum = dayNum * msecOfDay;
		long newMsec = getTimestampOfDateTime(date) + msecSum;
		LocalDateTime newDate = getDateTimeOfTimestamp(newMsec);
		return newDate;
	}

	/**
	 * LocalDateTime 转成自定义格式 时间 string
	 * @param localDateTime
	 * @param format
	 * @return
	 */
	public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	    return localDateTime.format(formatter);
	}

	/**
	 * LocalDateTime 转成自定义格式 时间 LocalDateTime
	 * @param localDateTime
	 * @param format
	 * @return
	 */
	public static LocalDateTime getDateTimeToFormat(LocalDateTime localDateTime, String format) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	    String strDate = localDateTime.format(formatter);
	    return StrToLocalDateTime(strDate,format);
	}

	/**
	 * string 转 LocalDateTime
	 * @param str
	 * @param dateformat
	 * @return
	 */
	public static LocalDateTime StrToLocalDateTime(String str,String dateformat) {
	    SimpleDateFormat format = new SimpleDateFormat(dateformat);
	    Date date = null;
	    LocalDateTime localDateTime = null;
	    try {
			date = format.parse(str);
			Instant instant = date.toInstant();
			ZoneId zone = ZoneId.systemDefault();
		    localDateTime = LocalDateTime.ofInstant(instant, zone);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	    return localDateTime;
	}
	
	/**
	 * 字符串转date
	 * @param str
	 * @param dateformat
	 * @return
	 * @throws ParseException
	 */
	public static Date StrToDate(String str,String dateformat) {  
		Date date = null;
		try {
			 SimpleDateFormat format = new SimpleDateFormat(dateformat);
			 date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	

	/**
	 * 时间戳转 LocalDateTime
	 * @param timeStamp
	 * @return
	 */
	public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
	    Instant instant = Instant.ofEpochMilli(timestamp);
	    ZoneId zone = ZoneId.systemDefault();
	    return LocalDateTime.ofInstant(instant, zone);
	}

	/**
	 * LocalDateTime 转 时间戳
	 * @param localDateTime
	 * @return
	 */
	public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    return instant.toEpochMilli();
	}
	
	/**
	 * 获得当天指定参数的时间戳
	 * @param hourOfDay   小时
	 * @param second      分钟
	 * @param minute      秒
	 * @param millisecond 毫秒
	 * @return
	 */
	public static long getTimestamp(int hourOfDay,int second,int minute,int millisecond) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal.getTimeInMillis();
	}

}
