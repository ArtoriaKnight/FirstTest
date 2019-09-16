package com.tutu2.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解决SimpleDateFormat并发错乱问题
 */
public class DateUtil {
    public final static String yyyyMMddHHmmss="yyyy-MM-dd HH:mm:ss";
    public final static String yyMMddHHmmss="yy-MM-dd HH:mm:ss";
    public final static String yyyyMMddHHmmss2="yyyyMMddHHmmss";
    public final static String yyyyMMdd="yyyyMMdd";
    public final static String yyyyMMdd2="yyyy-MM-dd";
    public final static String yyyyMMdd3="yyyy:MM:dd";
    /** 锁对象 */
    private static final Object lockObj = new Object();

    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new ConcurrentHashMap<>();
    public final static String yy_M_dd_HHmmss = "yy-MM-dd HH:mm:ss";

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
        if (tl == null) {
            synchronized (lockObj) {
                // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                tl = sdfMap.computeIfAbsent(pattern, p -> ThreadLocal.withInitial(() -> new SimpleDateFormat(p)));
            }
        }

        return tl.get();
    }


    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static DateFormat getDateFormat(String pattern){
        return getSdf(pattern);
    }
    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }

    public static Date addDay(Date day,int addDays){
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.add(Calendar.DATE,addDays);
        return cal.getTime();
    }

    public static Date diffDay(Date day,int diffDays){
        return addDay(day,0-diffDays);
    }

    /**
     * 计算时间差(天)
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDiffDays(Date startTime, Date endTime) {
        long nd = 1000 * 24 * 60 * 60;
        long diff;
        long day = 0;
        diff = endTime.getTime() -startTime.getTime();
        day = diff / nd;
        return day;
    }

    /**
     * 计算时间差(毫秒)
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDiff(Date startTime, Date endTime) {
        long diff = endTime.getTime() -startTime.getTime();
        return diff;
    }

}