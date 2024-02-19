package edu.ucsd.cse110.successorator.lib.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.ucsd.cse110.successorator.lib.domain.Date;

public class DateUpdater {
    private static final int DELAY_HOUR = 2;
    private static final int MIN_IN_HOUR = 60;
    private static final int SEC_IN_MIN = 60;

    private static final int MILLISEC_IN_SEC = 1000;
    private Date date;

    private DateFormatter formatter;
    private MutableSubject<String> dateString;

    public DateUpdater(){
        Calendar calendar = new GregorianCalendar();
        date = new Date(calendar);
        delay();
        formatter = new DateFormatter();
        dateString = new SimpleSubject<>();
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
    }

    public void dateIncrement(){
        date.dayIncrement();
        checkDate();
    }
    public void checkDate(){
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
    }

    public void syncDate(){
        delay();
        checkDate();
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }
    public void delay(){
        Long curMillis = System.currentTimeMillis();
        curMillis = curMillis - DELAY_HOUR*MIN_IN_HOUR*SEC_IN_MIN*MILLISEC_IN_SEC;
        date.getCalendar().setTimeInMillis(curMillis);
    }
}