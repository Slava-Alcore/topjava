package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt,LocalTime startTime
            ,LocalTime endTime) {
        if (startTime!=null) {
            if (endTime!=null) {
                return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
            } else {
                return lt.compareTo(startTime) >= 0;
            }
        } else {
            if (endTime!=null) {
                return lt.compareTo(endTime) <= 0;
            } else {
                return true;
            }
        }

    }

    public static boolean isBetweenDate(LocalDate ld,LocalDate startDate
            ,LocalDate endDate){
        if (startDate!=null) {
            if (endDate!=null) {
                return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
            } else {
                return ld.compareTo(startDate) >= 0;
            }
        } else {
            if (endDate!=null) {
                return ld.compareTo(endDate) <= 0;
            } else {
                return true;
            }
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

