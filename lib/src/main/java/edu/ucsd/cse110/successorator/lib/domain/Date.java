package edu.ucsd.cse110.successorator.lib.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Objects;


public class Date implements Cloneable, Comparable<Date>, Serializable {
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
        return calendar.get(Calendar.DAY_OF_MONTH)==date.getCalendar().get(Calendar.DAY_OF_MONTH)
                &&calendar.get(Calendar.MONTH)==date.getCalendar().get(Calendar.MONTH)
                &&calendar.get(Calendar.YEAR)==date.getCalendar().get(Calendar.YEAR);
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

    @Override
    public int compareTo(Date d){
        if (calendar.get(Calendar.YEAR)!=d.getCalendar().get(Calendar.YEAR)){
            return calendar.get(Calendar.YEAR)-d.getCalendar().get(Calendar.YEAR);
        } else if(calendar.get(Calendar.MONTH)!=d.getCalendar().get(Calendar.MONTH)){
            return calendar.get(Calendar.MONTH)-d.getCalendar().get(Calendar.MONTH);
        } else {
            return calendar.get(Calendar.DAY_OF_MONTH)-d.getCalendar().get(Calendar.DAY_OF_MONTH);
        }
    }
}
