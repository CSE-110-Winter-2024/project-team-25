package edu.ucsd.cse110.successorator.lib.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalWithDate;
import edu.ucsd.cse110.successorator.lib.domain.Type;

public class GoalDateComparator {
    public static Predicate<Goal> createGoalPredicate(Date targetDate, Type type){
        switch (type){
            case TODAY:
                return goal-> (goal instanceof DatedGoal
                            &&((DatedGoal) goal).getDate().compareTo(targetDate)<=0);
            case TOMORROW:
                return goal -> (goal instanceof DatedGoal
                                &&((DatedGoal) goal).getDate().equals(targetDate));
            case RECURRENCE:
                return goal -> (goal instanceof RecurringGoal);
            case PENDING:
                return null;
        }
        return null;
    }
    public static List<Goal> createGoalListWithDate(Date targetDate, List<Goal>goals, Type type){
        return goals
            .stream()
            .filter(GoalDateComparator.createGoalPredicate(targetDate, type))
            .sorted()
            .collect(Collectors.toList());
    }

    public static boolean hasRecurringWithDateGoalOn(Date date, int id, List<Goal> goals){
        boolean result = goals.stream()
                .filter(goal->goal instanceof RecurringGoalWithDate
                        &&((RecurringGoalWithDate)goal).getRecurrenceID()==id
                        &&((RecurringGoalWithDate)goal).getDate().equals(date))
                .count() != 0;
        return result;
    }

    public static boolean hasRedundancy(RecurringGoalWithDate newGoal, List<Goal> goals){
        return goals.stream().anyMatch(goal -> goal instanceof RecurringGoalWithDate
                && ((RecurringGoalWithDate)goal).equal(newGoal));
    }

}
