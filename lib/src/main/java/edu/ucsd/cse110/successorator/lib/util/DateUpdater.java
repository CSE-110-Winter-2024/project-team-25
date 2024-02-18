package edu.ucsd.cse110.successorator.lib.util;

import java.util.GregorianCalendar;

import edu.ucsd.cse110.successorator.lib.domain.Date;

public class DateUpdater {
    private Date date;

    private DateFormatter formatter;
    private MutableSubject<String> dateString;

    public DateUpdater(){
        date = new Date(new GregorianCalendar());
        dateString = new SimpleSubject<>();
        formatter = new DateFormatter();
    }

    public void dateIncrement(){
        date.dayIncrement();
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
    }

    public MutableSubject<String> getDateString(){
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
        return dateString;
    }
}