package edu.ucsd.cse110.successorator.lib.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.ucsd.cse110.successorator.lib.domain.Date;

public class DateUpdater {
    private static final int DELAY_HOUR = 2;

    private static final int HOUR_IN_DAY =24;
    private static final int MIN_IN_HOUR = 60;
    private static final int SEC_IN_MIN = 60;
    private static final int MILLISEC_IN_SEC = 1000;
    private static final int MILLISEC_IN_HOUR = MIN_IN_HOUR*SEC_IN_MIN*MILLISEC_IN_SEC;

    private Date date;

    private DateFormatter formatter;
    private MutableSubject<String> dateString;

    private int dayForward = 0;

    public DateUpdater(){
        Calendar calendar = new GregorianCalendar();
        date = new Date(calendar);
        formatter = new DateFormatter();
        dateString = new SimpleSubject<>();
        syncDate();
    }

    public void dateIncrement(){
        mockTime(DELAY_HOUR, ++dayForward);
        checkDate();
    }
    public void checkDate(){
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
    }

    public void syncDate(){
        dayForward = 0;
        mockTime(DELAY_HOUR, dayForward);
        checkDate();
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }
    public void mockTime(int delayHour, int dayForward){
        Long curMillis = System.currentTimeMillis();
        curMillis = curMillis - delayHour*MILLISEC_IN_HOUR + dayForward * HOUR_IN_DAY * MILLISEC_IN_HOUR;;
        date.getCalendar().setTimeInMillis(curMillis);
    }
}