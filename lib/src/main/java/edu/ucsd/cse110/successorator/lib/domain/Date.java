package edu.ucsd.cse110.successorator.lib.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class Date{
    private Calendar calendar;
    private String format = "EEEE, MM/dd";

    public Date(Calendar calendar){
        this.calendar = calendar;
    }

    public void dayIncrement(){
        calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
//    public void daySync(){
//        this.localDateTime = localDateTime.now();
//    }

    public Calendar getCalendar() {
        return calendar;
    }
}
