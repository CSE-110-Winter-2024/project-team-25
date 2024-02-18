package edu.ucsd.cse110.successorator.lib.util;

import java.time.Duration;
import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.lib.domain.Date;

public class DateUpdater {
    private Date date;

    private DateFormatter formatter;
    private MutableSubject<String> dateString;

    public DateUpdater(){
        LocalDateTime now = LocalDateTime.now();

        date = new Date(now);
        String s=date.getTime();
        formatter = new DateFormatter();
        dateString = new SimpleSubject<>();
        dateString.setValue(formatter.getFormatDate(date.getLocalDateTime()));
    }

    public void dateIncrement(){
        date.dayIncrement();
        checkDate();
    }

    public void checkDate(){
        dateString.setValue(formatter.getFormatDate(date.getLocalDateTime()));
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }
}