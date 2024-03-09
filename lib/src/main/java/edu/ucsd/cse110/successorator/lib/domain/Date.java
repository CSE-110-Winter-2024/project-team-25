package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Calendar;
import java.util.Objects;


public class Date implements Cloneable {
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

    @Override
    public Date clone() {
        try {
            Object superDate = super.clone();
            return new Date((Calendar) calendar.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }


}
