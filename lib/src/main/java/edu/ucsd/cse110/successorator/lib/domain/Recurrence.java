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

public class Recurrence implements Serializable {
    private @NonNull Date firstOccurrence;
    private @NonNull Date nextOccurrence;
    private @Nullable Date endDate;
    private @NonNull List<Period> datePattern;
    private int index;

    public Recurrence(@NonNull Date firstOccurrence, @NonNull List<Period> datePattern) {
        this.firstOccurrence = firstOccurrence;
        this.nextOccurrence = firstOccurrence;
        this.datePattern = datePattern;
        this.index = -1;
    }

    public boolean showRecurringGoal(Date today){

        return true;
    }

    public Date applyRecurrence() {
        Date nextDate = nextOccurrence.clone();
        if(index != -1) {
            nextDate.getCalendar().add(Calendar.YEAR, datePattern.get(index).getYears());
            nextDate.getCalendar().add(Calendar.MONTH, datePattern.get(index).getMonths());
            if(datePattern.get(index).getDays() != 0) {
                nextDate.getCalendar().add(Calendar.DATE, datePattern.get(index).getDays());
            } else if(datePattern.get(index).getMonths()!=0) {
                nextDate.getCalendar().set(Calendar.WEEK_OF_MONTH, nextOccurrence.getCalendar().get(Calendar.WEEK_OF_MONTH));
                nextDate.getCalendar().set(Calendar.DAY_OF_WEEK, nextOccurrence.getCalendar().get(Calendar.DAY_OF_WEEK));
            }
            nextOccurrence = nextDate;
        }
        index = (index + 1) % datePattern.size();
        return nextDate;
    }
    public Date getFirstOccurrence() {
        return firstOccurrence;
    }
    public Date getNextOccurrence() {
        return nextOccurrence;
    }
}
