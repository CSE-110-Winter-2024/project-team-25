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
    public Date(Calendar calendar){
        this.calendar = calendar;
    }
    public void hourAdvance(int hourChange){
        calendar.add(Calendar.HOUR, hourChange);
    }
    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return Objects.equals(calendar, date.calendar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendar);
    }
}
