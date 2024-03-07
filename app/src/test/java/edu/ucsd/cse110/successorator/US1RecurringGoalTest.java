package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createDailyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createWeeklyRecurrence;

import org.junit.Test;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

public class US1RecurringGoalTest {
    @Test
    public void testConstructorAndGetters()
    {
        int id = 1;
        String content = "Example Goal";
        boolean isComplete = false;
        int sortOrder = 0;
        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar);
        Recurrence recurrence = createDailyRecurrence(startDate);
        Context context = Context.SCHOOL;

        RecurringGoal goal = new RecurringGoal(id, content, isComplete, sortOrder, context, recurrence);

        assertEquals(id, goal.getId());
        assertEquals(content, goal.getContent());
        assertEquals(isComplete, goal.isComplete());
        assertEquals(sortOrder, goal.getSortOrder());
        assertEquals(context, goal.getContext());
        assertEquals(recurrence, goal.getRecurrence());

        // Test with null content
        RecurringGoal nullContentGoal = new RecurringGoal(2, null, true, 1, context, recurrence);
        assertNull(nullContentGoal.getContent());

        // Test with negative sortOrder
        RecurringGoal negativeSortOrderGoal = new RecurringGoal(3, "Negative Sort Order", false, -1, context, recurrence);
        assertEquals(-1, negativeSortOrderGoal.getSortOrder());

        // Test with zero sortOrder
        RecurringGoal zeroSortOrderGoal = new RecurringGoal(4, "Zero Sort Order", true, 0, context, recurrence);
        assertEquals(0, zeroSortOrderGoal.getSortOrder());

        // Test with None context
        RecurringGoal noneContextGoal = new RecurringGoal(5, "None Context", true, 0, Context.NONE, recurrence);
        assertEquals(Context.NONE, noneContextGoal.getContext());

        // Test with maximum integer sortOrder
        int maxIntSortOrder = Integer.MAX_VALUE;
        RecurringGoal maxIntSortOrderGoal = new RecurringGoal(5, "Max Int Sort Order", false,
                maxIntSortOrder, context, recurrence);
        assertEquals(maxIntSortOrder, maxIntSortOrderGoal.getSortOrder());

        //Test with different Recurrence
        Recurrence weeklyRecurrence = createWeeklyRecurrence(startDate);
        Recurrence monthlyRecurrence = createWeeklyRecurrence(startDate);
        Recurrence yearlyRecurrence = createWeeklyRecurrence(startDate);

        RecurringGoal monthlyRecurrenceGoal = new RecurringGoal(id, content, isComplete, sortOrder, context, monthlyRecurrence);
        RecurringGoal yearlyRecurrenceGoal = new RecurringGoal(id, content, isComplete, sortOrder, context, yearlyRecurrence);
        RecurringGoal weeklyRecurrenceGoal = new RecurringGoal(id, content, isComplete, sortOrder, context, weeklyRecurrence);

        assertNotEquals(goal, weeklyRecurrenceGoal);
        assertNotEquals(goal, monthlyRecurrenceGoal);
        assertNotEquals(goal, yearlyRecurrenceGoal);
        assertNotEquals(weeklyRecurrenceGoal, monthlyRecurrenceGoal);
        assertNotEquals(monthlyRecurrenceGoal, yearlyRecurrenceGoal);
        assertNotEquals(weeklyRecurrenceGoal, yearlyRecurrenceGoal);

        // Test with different instances
        RecurringGoal sameAttributesGoal = new RecurringGoal(id, content, isComplete, sortOrder, context, recurrence);
        RecurringGoal differentIdGoal = new RecurringGoal(2, content, isComplete, sortOrder, context, recurrence);
        RecurringGoal differentContentGoal = new RecurringGoal(id, "Different Content", isComplete,
                sortOrder, context, recurrence);
        RecurringGoal differentIsCompleteGoal = new RecurringGoal(id, content, true, sortOrder, context, recurrence);
        RecurringGoal differentSortOrderGoal = new RecurringGoal(id, content, isComplete, 1, context, recurrence);
        RecurringGoal differentContextGoal = new RecurringGoal(id, content, isComplete, sortOrder, Context.ERRANDS, recurrence);

        assertEquals(goal, sameAttributesGoal);
        assertNotEquals(goal, differentIdGoal);
        assertNotEquals(goal, differentContentGoal);
        assertNotEquals(goal, differentIsCompleteGoal);
        assertNotEquals(goal, differentSortOrderGoal);
        assertNotEquals(goal, differentContextGoal);
    }

    @Test
    public void testEqualsAndHashCode()
    {
        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar);
        Recurrence recurrence = createDailyRecurrence(startDate);
        RecurringGoal goal1 = new RecurringGoal(1, "Goal 1", true, 1, Context.SCHOOL, recurrence);
        RecurringGoal goal2 = new RecurringGoal(1, "Goal 1", true, 1, Context.SCHOOL, recurrence);
        RecurringGoal goal3 = new RecurringGoal(2, "Goal 2", false, 2, Context.SCHOOL, recurrence);

        // Reflexive
        assertEquals(goal1, goal1);

        // Symmetric
        assertEquals(goal1, goal2);
        assertEquals(goal2, goal1);

        // Not equal to another object
        assertNotEquals(goal1, goal3);

        // Not equal to null
        assertNotEquals(null, goal1);

        // Hash code consistency
        assertEquals(goal1.hashCode(), goal2.hashCode());
    }

//    @Test
//    public void testComparable()
//    {
//        Calendar calendar = Calendar.getInstance();
//        Date startDate = new Date(calendar);
//        Recurrence recurrence = createDailyRecurrence(startDate);
//        Comparable goal1 = new Goal(1, "Goal 1", true, 1, recurrence);
//        Comparable goal2 = new Goal(1, "Goal 1", true, 1);
//        Comparable goal3 = new Goal(2, "Goal 2", false, 2);// Reflexive
//
//        assertEquals(goal1, goal1);
//
//        // Symmetric
//        assertEquals(goal1, goal2);
//        assertEquals(goal2, goal1);
//
//        // Not equal to another object
//        assertNotEquals(goal1, goal3);
//
//        // Not equal to null
//        assertNotEquals(null, goal1);
//
//        // Hash code consistency
//        assertEquals(goal1.hashCode(), goal2.hashCode());
//    }
}
