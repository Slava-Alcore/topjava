package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        if (s==null || s.isEmpty())
            return null;
        return LocalDate.from(getDateFormat(locale).parse(s));
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        if (localDate==null)
            return "";
        return getDateFormat(locale).format(localDate);
    }

    private DateTimeFormatter getDateFormat(Locale locale) {
        return DateTimeFormatter.ISO_LOCAL_DATE.localizedBy(locale);
    }
}
