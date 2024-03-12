package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        Recurrence clone = this.clone();
        while(clone.nextOccurrence.getCalendar().before(date.getCalendar())) {
            clone.applyRecurrence();
        }
        return clone.nextOccurrence.getCalendar().equals(date.getCalendar());
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
