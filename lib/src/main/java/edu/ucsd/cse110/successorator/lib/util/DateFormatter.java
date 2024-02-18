package edu.ucsd.cse110.successorator.lib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatter {
    private String pattern = "EEEE, MM/dd";
    SimpleDateFormat formatter;
    public DateFormatter(){
        formatter = new SimpleDateFormat(pattern);
    }
    public String getFormatDate(Calendar calendar){
        return formatter.format(calendar.getTime());
    }
}
