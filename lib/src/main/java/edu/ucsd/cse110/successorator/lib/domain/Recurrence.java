package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Recurrence implements Serializable, Cloneable {
    private @NonNull Date firstOccurrence;
    private @NonNull Date nextOccurrence;
    private @Nullable Date endDate;
    private @NonNull List<Period> datePattern;
    private int index;

    public Recurrence(@NonNull Date firstOccurrence, @NonNull List<Period> datePattern) {
        this.firstOccurrence = firstOccurrence;
        this.nextOccurrence = firstOccurrence;
        this.datePattern = datePattern;
        this.index = 0;
    }

    public Date applyRecurrence() {
        Date nextDate = nextOccurrence.clone();
        nextDate.getCalendar().add(Calendar.YEAR, datePattern.get(index).getYears());
        nextDate.getCalendar().add(Calendar.MONTH, datePattern.get(index).getMonths());
        if(datePattern.get(index).getMonths() == 0 || datePattern.get(index).getDays() != 0) {
            nextDate.getCalendar().add(Calendar.DAY_OF_MONTH, datePattern.get(index).getDays());
        } else {
            nextDate.getCalendar().set(Calendar.WEEK_OF_MONTH, nextOccurrence.getCalendar().get(Calendar.WEEK_OF_MONTH));
            nextDate.getCalendar().set(Calendar.DAY_OF_WEEK, nextOccurrence.getCalendar().get(Calendar.DAY_OF_WEEK));
        }
        nextOccurrence = nextDate;
        index = (index + 1) % datePattern.size();
        return nextDate;
    }
    public Date getFirstOccurrence() {
        return firstOccurrence;
    }
    public Date getNextOccurrence() {
        return nextOccurrence;
    }
    public boolean isFutureRecurrence(Date date) {
        Calendar recurrence_cal = new GregorianCalendar();
        if(date.compareTo(firstOccurrence)<0){
            return false;
        }
        recurrence_cal.set(firstOccurrence.getCalendar().get(Calendar.YEAR),
                                    firstOccurrence.getCalendar().get(Calendar.MONTH),
                                    firstOccurrence.getCalendar().get(Calendar.DAY_OF_MONTH),
                                    0,0,0);
        int dayOfWeek = firstOccurrence.getCalendar().get(Calendar.DAY_OF_WEEK);
        int WeekOfMonth = firstOccurrence.getCalendar().get(Calendar.WEEK_OF_MONTH);
        Calendar date_cal = new GregorianCalendar();
        date_cal.set(date.getCalendar().get(Calendar.YEAR),
                date.getCalendar().get(Calendar.MONTH),
                date.getCalendar().get(Calendar.DAY_OF_MONTH),
                0,0,0);
        Long differenceInMillis = date_cal.getTimeInMillis() - recurrence_cal.getTimeInMillis();
        int differenceInDays = Math.toIntExact(TimeUnit.MILLISECONDS.toDays(differenceInMillis));
        int differenceInYears = date.getCalendar().get(Calendar.YEAR) -
                                firstOccurrence.getCalendar().get(Calendar.YEAR);
        if(datePattern.get(index).getDays() != 0){
            return differenceInDays%datePattern.get(index).getDays()==0;
        }
        if(datePattern.get(index).getMonths() != 0){
            Calendar expected_cal = new GregorianCalendar();
            expected_cal.set(Calendar.YEAR, date.getCalendar().get(Calendar.YEAR));
            expected_cal.set(Calendar.MONTH, date.getCalendar().get(Calendar.MONTH));
            expected_cal.set(Calendar.WEEK_OF_MONTH, WeekOfMonth);
            expected_cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            return date.equals(new Date(expected_cal));
        }
        if(datePattern.get(index).getYears() != 0){

            return differenceInYears%datePattern.get(index).getYears()==0
                    &&date_cal.get(Calendar.MONTH)==recurrence_cal.get(Calendar.MONTH)
                    &&date_cal.get(Calendar.DAY_OF_MONTH)==recurrence_cal.get(Calendar.DAY_OF_MONTH);
        }
        return false;
    }

    @Override
    public Recurrence clone() {
        try {
            Recurrence clone = (Recurrence) super.clone();
            clone.firstOccurrence = firstOccurrence.clone();
            clone.nextOccurrence = nextOccurrence.clone();
            clone.index = index;
            clone.datePattern = new ArrayList<>(datePattern);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }


}