package edu.ucsd.cse110.successorator.lib.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return Objects.equals(calendar, date.calendar) && Objects.equals(format, date.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendar, format);
    }
}
