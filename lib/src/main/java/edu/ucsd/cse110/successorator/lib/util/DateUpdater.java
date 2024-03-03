package edu.ucsd.cse110.successorator.lib.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import edu.ucsd.cse110.successorator.lib.domain.Date;

public class DateUpdater {
    private Date date;
    private DateFormatter formatter;
    private MutableSubject<String> dateString;
    public DateUpdater(int delayHour){
        Calendar calendar = new GregorianCalendar();
        date = new Date(calendar);
        formatter = new DateFormatter();
        dateString = new SimpleSubject<>();
        dateUpdate(delayHour);
    }

    public void dateUpdate(int hourChange){
        date.hourAdvance(hourChange);
        checkDate();
    }
    public void syncDate(){
        date.syncDate();
        checkDate();
    }

    public void checkDate(){
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }

    public Date getDate() {
        return date;
    }
}