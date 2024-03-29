package edu.ucsd.cse110.successorator.lib.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import edu.ucsd.cse110.successorator.lib.domain.Date;

public class DateUpdater implements TimeKeeper{
    private Date date;
    private DateFormatter formatter;
    private MutableSubject<String> dateString;
    private MutableSubject<Date> dateSubject;

    public DateUpdater(int delayHour){
        Calendar calendar = new GregorianCalendar();
        date = new Date(calendar);
        dateSubject = new SimpleSubject<>();
        //dateSubject.setValue(date);
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
        dateSubject.setValue(date);
        dateString.setValue(formatter.getFormatDate(date.getCalendar()));
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }
//
//    public MutableSubject<Date> getDateSubject(){
//        return dateSubject;
//    }


    public DateFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public Date getDate() {
        return date;
    }
    @Override
    public MutableSubject<Date> getDateAsSubject(){
        return dateSubject;
    }
}