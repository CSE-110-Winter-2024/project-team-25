package edu.ucsd.cse110.successorator.lib.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.ucsd.cse110.successorator.lib.domain.Date;

public class DateUpdater {
    private Date date;

    private DateFormatter formatter;
    private MutableSubject<String> dateString;

    public DateUpdater(){
        Long curMillis = System.currentTimeMillis();
        curMillis = curMillis - 2*60*60*1000;
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(curMillis);
        date = new Date(calendar);
        formatter = new DateFormatter();
        dateString = new SimpleSubject<>();
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
    }

    public void dateIncrement(){
        date.dayIncrement();
        checkDate();
    }

    public void checkDate(){
        Long curMillis = System.currentTimeMillis();
        curMillis = curMillis - 2*60*60*1000;
        date.getCalendar().setTimeInMillis(curMillis);
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }
}