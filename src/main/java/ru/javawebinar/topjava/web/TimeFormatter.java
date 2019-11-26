package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        if (s==null || s.isEmpty())
            return null;
        return LocalTime.from(getDateFormat(locale).parse(s));
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        if (localTime==null)
            return "";
        return getDateFormat(locale).format(localTime);
    }

    private DateTimeFormatter getDateFormat(Locale locale) {
        return DateTimeFormatter.ISO_LOCAL_TIME.localizedBy(locale);
    }
}
