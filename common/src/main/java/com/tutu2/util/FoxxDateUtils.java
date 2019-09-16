package com.tutu2.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class FoxxDateUtils {

    public static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return java.sql.Timestamp.valueOf(localDateTime);
    }

    public static int daysBetween(LocalDate start, LocalDate end) {
        Long days = DAYS.between(start, end);
        return days.intValue();
    }

    /*** testing */
    public static void main(String[] args) {
        LocalDate start = LocalDate.of(2019, 3, 13);
        LocalDate now = LocalDate.now();
        System.out.println(DAYS.between(start, now));
    }

}
