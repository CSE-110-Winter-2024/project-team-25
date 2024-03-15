package edu.ucsd.cse110.successorator.lib.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.PendingGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalWithDate;
import edu.ucsd.cse110.successorator.lib.domain.Type;
import edu.ucsd.cse110.successorator.lib.domain.Context;

public class GoalDateComparator {
    public static Predicate<Goal> createGoalPredicate(Date targetDate, Type type, boolean b, Context c){
        if (b){
            switch (type){
                case TODAY:
                    return goal-> (goal instanceof DatedGoal
                            &&((DatedGoal) goal).getDate().compareTo(targetDate)<=0
                            && (goal.getContext()==c));
                case TOMORROW:
                    return goal -> (goal instanceof DatedGoal
                            &&((DatedGoal) goal).getDate().equals(targetDate)
                            && (goal.getContext()==c));
                case RECURRENCE:
                    return goal -> (goal instanceof RecurringGoal
                            && (goal.getContext()==c));
                case PENDING:
                    return goal -> (goal instanceof PendingGoal
                            && (goal.getContext()==c));
            }
        } else{
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
                    return goal -> (goal instanceof PendingGoal);
            }
        }

        return null;
    }
    public static List<Goal> createGoalListWithDate(Date targetDate, List<Goal>goals, Type type, boolean b, Context c){
        return goals
            .stream()
            .filter(GoalDateComparator.createGoalPredicate(targetDate, type, b, c))
            .sorted()
            .collect(Collectors.toList());
    }
    /*public static boolean overrideSameRecurringTask (Date date, int recurrenceId, List<Goal> goals){
        // all recurring goal recurred on that given day with same recurringId as recurrence
        // completeness should be preserved
        goals.stream().anyMatch(goal -> goal instanceof RecurringGoalWithDate && goal)
    }
    public static void updateDateForRecurringGoalWithDate (Date date, List<Goal> goals) {
        /*goals.stream().filter(goal -> goal instanceof RecurringGoal
                            && )
    }*/
    public static boolean hasRedundancy(Date date, RecurringGoalWithDate targetGoal, List<Goal> goals){
        return goals.stream()
                .filter(goal -> goal.getId()!=targetGoal.getId())
                .anyMatch(goal -> goal instanceof RecurringGoalWithDate
                && ((RecurringGoalWithDate)goal).getDate().compareTo(date)<=0
                && targetGoal.getRecurrenceID()==((RecurringGoalWithDate) goal).getRecurrenceID()
                &&!goal.isComplete()&&goal.getSortOrder()>targetGoal.getSortOrder());
    }

}
