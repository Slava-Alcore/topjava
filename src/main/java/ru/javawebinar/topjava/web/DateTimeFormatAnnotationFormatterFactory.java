package ru.javawebinar.topjava.web;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DateTimeFormatAnnotationFormatterFactory
implements AnnotationFormatterFactory<DateTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(LocalDate.class, LocalTime.class, String.class));
    }

    @Override
    public Printer<?> getPrinter(DateTimeFormat dateTimeFormat, Class<?> aClass) {
        return getFormatter(dateTimeFormat,aClass);
    }

    @Override
    public Parser<?> getParser(DateTimeFormat dateTimeFormat, Class<?> aClass) {
        return getFormatter(dateTimeFormat,aClass);
    }

    private Formatter<?> getFormatter (DateTimeFormat dateTimeFormat, Class<?> aClass){
        if (dateTimeFormat.iso()==DateTimeFormat.ISO.DATE){
            return new DateFormatter();
        } else if (dateTimeFormat.iso()==DateTimeFormat.ISO.TIME){
            return new TimeFormatter();
        }
        return null;
    }
}
