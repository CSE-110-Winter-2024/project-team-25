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

    public Date applyRecurrence() {
        Date nextDate = nextOccurrence.clone();
        if(index != -1) {
            nextDate.getCalendar().add(Calendar.YEAR, datePattern.get(index).getYears());
            nextDate.getCalendar().add(Calendar.MONTH, datePattern.get(index).getMonths());
            nextDate.getCalendar().add(Calendar.DAY_OF_MONTH, datePattern.get(index).getDays());
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
