package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createDailyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createWeeklyRecurrence;

import org.junit.Test;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalWithDate;

public class US7RecurringGoalWithDate {
    @Test
    public void compare()
    {

        int id = 1;
        String content = "Example Goal";
        boolean isComplete = false;
        int sortOrder = 0;
        Context context = Context.HOME;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        Date d = new Date(calendar);
        Goal goalHome = new Goal(id, content, isComplete, sortOrder, Context.HOME);
        Goal goalWork = new Goal(id, content, isComplete, sortOrder, Context.WORK);
        DatedGoal g1 = new RecurringGoalWithDate(goalHome, d, 0);
        DatedGoal g2 = new RecurringGoalWithDate(goalHome, d, 0);
        assertEquals(0, g1.compareTo(g2));



    }

    @Test
    public void testConstructors(){
        int id = 1;
        String content = "Example Goal";
        boolean isComplete = false;
        int sortOrder = 0;
        Context context = Context.HOME;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        Date d = new Date(calendar);
        int recurrenceId = 0;
        RecurringGoalWithDate goal = new RecurringGoalWithDate(id, content, isComplete, sortOrder, Context.ERRANDS,d, recurrenceId);
        assertEquals(id, goal.getId());
        assertEquals(content, goal.getContent());
        assertEquals(isComplete, goal.isComplete());
        assertEquals(sortOrder, goal.getSortOrder());
        assertNotEquals(context, goal.getContext());
        assertEquals(recurrenceId, goal.getRecurrenceID());

        RecurringGoalWithDate goal1 = new RecurringGoalWithDate(id, content, isComplete, sortOrder,d, recurrenceId);

        assertEquals(id, goal1.getId());
        assertEquals(content, goal1.getContent());
        assertEquals(isComplete, goal1.isComplete());
        assertEquals(sortOrder, goal1.getSortOrder());
        assertNotEquals(context, goal1.getContext());
        assertEquals(recurrenceId, goal1.getRecurrenceID());


    }
    @Test
    public void testComparable()
    {
        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar);
        Recurrence recurrence = createDailyRecurrence(startDate);

        Comparable goal1 = new RecurringGoalWithDate(1, "Goal 1", true, 1, startDate, 0);
        Comparable goal2 = new RecurringGoalWithDate(1, "Goal 1", true, 1, startDate, 0);
        Comparable goal3 = new RecurringGoalWithDate(0, "Goal 2", false, 2, startDate, 2); // Reflexive

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
}
