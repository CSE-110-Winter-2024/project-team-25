package edu.ucsd.cse110.successorator.lib.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateFormatter {
    private String pattern = "E, MMM dd";
    DateTimeFormatter formatter;
    public DateFormatter(){
        formatter = DateTimeFormatter.ofPattern(pattern);
    }
    public String getFormatDate(LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }
}
