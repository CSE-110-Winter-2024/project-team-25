package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class RecurrenceFactory {
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
        return new Recurrence(startDate, List.of(Period.ofDays(1)));
    }
    public static Recurrence createWeeklyRecurrence(@NonNull Date startDate) {
        return new Recurrence(startDate, List.of(Period.ofWeeks(1)));
    }
    public static Recurrence createMonthlyRecurrence(@NonNull Date startDate) {
        return new Recurrence(startDate, List.of(Period.ofMonths(1)));
    }
    public static Recurrence createYearlyRecurrence(@NonNull Date startDate) {
        return new Recurrence(startDate, List.of(Period.ofYears(1)));
    }
}
