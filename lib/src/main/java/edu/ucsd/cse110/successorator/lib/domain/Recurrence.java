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
    public static Recurrence createDaysOfWeekRecurrence(@NonNull Date startDate, List<DayOfWeek> daysOfWeek) {
        List<Integer> sortedDaysOfWeek = daysOfWeek.stream()
                                                   .map(DayOfWeek::getValue)
                                                   .sorted()
                                                   .collect(Collectors.toList());
        int startDayOfWeek = startDate.getCalendar().get(Calendar.DAY_OF_WEEK);
        int first = 0;
        while(first < daysOfWeek.size() && startDayOfWeek < sortedDaysOfWeek.get(first))
            first++;
        first %= daysOfWeek.size();
        int curr = first;
        List<Period> datePattern = new ArrayList<>();
        while(datePattern.size() != daysOfWeek.size()) {
            datePattern.add(Period.ofDays(
                    Math.floorMod(sortedDaysOfWeek.get((curr + 1) % sortedDaysOfWeek.size()) -
                                  sortedDaysOfWeek.get(curr), 7)));
            curr = (curr + 1) % sortedDaysOfWeek.size();
        }
        startDate = startDate.clone();
        startDate.getCalendar().add(Calendar.DAY_OF_WEEK, Math.floorMod(sortedDaysOfWeek.get(first) - startDayOfWeek, 7));
        return new Recurrence(startDate, datePattern);
    }
    public static Recurrence createDailyRecurrence(@NonNull Date startDate) {
        List<Period> datePattern = new ArrayList<>();
        datePattern.add(Period.ofDays(1));
        return new Recurrence(startDate, datePattern);
    }
    public static Recurrence createWeeklyRecurrence(@NonNull Date startDate) {
        List<Period> datePattern = new ArrayList<>();
        datePattern.add(Period.ofWeeks(1));
        return new Recurrence(startDate, datePattern);
    }
    public static Recurrence createMonthlyRecurrence(@NonNull Date startDate, int dayOfMonth) {
        List<Period> datePattern = new ArrayList<>();
        datePattern.add(Period.ofMonths(1));
        startDate = startDate.clone();
        if(startDate.getCalendar().get(Calendar.DAY_OF_MONTH) > dayOfMonth)
            startDate.getCalendar().add(Calendar.MONTH, 1);
        startDate.getCalendar().set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return new Recurrence(startDate, datePattern);
    }
    public static Recurrence createYearlyRecurrence(@NonNull Date startDate, int month, int dayOfMonth) {
        List<Period> datePattern = new ArrayList<>();
        datePattern.add(Period.ofYears(1));
        startDate = startDate.clone();
        if(startDate.getCalendar().get(Calendar.MONTH) > month ||
           (startDate.getCalendar().get(Calendar.MONTH) == month &&
            startDate.getCalendar().get(Calendar.DAY_OF_MONTH) > dayOfMonth))
            startDate.getCalendar().add(Calendar.YEAR, 1);
        startDate.getCalendar().set(Calendar.MONTH, month);
        startDate.getCalendar().set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return new Recurrence(startDate, datePattern);
    }

    public Date applyRecurrence() {
        Date nextDate = nextOccurrence.clone();
        if(index != -1) {
            nextDate.getCalendar().add(Calendar.DATE, datePattern.get(index).getDays());
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
