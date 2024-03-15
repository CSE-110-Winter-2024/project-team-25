package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class US1DatedGoalTest {
    @Test
    public void testConstructorAndGetters()
    {
        int id = 1;
        String content = "Example Goal";
        boolean isComplete = false;
        int sortOrder = 0;
        Context context = Context.HOME;
        Calendar calendar1 = new GregorianCalendar(2024, Calendar.FEBRUARY, 18);
        Calendar calendar2 = new GregorianCalendar(2023, Calendar.FEBRUARY, 18);
        Date date = new Date(calendar1);

        //Test Constructor #1
        DatedGoal goal = new DatedGoal(id, content, isComplete, sortOrder, context, date);

        assertEquals(id, goal.getId());
        assertEquals(content, goal.getContent());
        assertEquals(isComplete, goal.isComplete());
        assertEquals(sortOrder, goal.getSortOrder());
        assertEquals(context, goal.getContext());
        assertEquals(date, goal.getDate());

        //Test Constructor #2
        DatedGoal goal1 = new DatedGoal(id, content, isComplete, sortOrder, date);

        assertNotEquals(goal,goal1);

        assertEquals(id, goal1.getId());
        assertEquals(content, goal1.getContent());
        assertEquals(isComplete, goal1.isComplete());
        assertEquals(sortOrder, goal1.getSortOrder());
        assertNotEquals(context, goal1.getContext());
        assertEquals(date, goal1.getDate());

        //Test Constructor #3
        Goal simpleGoal = new Goal(id, content, isComplete, sortOrder, context);

        DatedGoal goal2 = new DatedGoal(simpleGoal, date);

        assertNotEquals(goal1,goal2);
        assertEquals(goal, goal2);

        assertEquals(id, goal2.getId());
        assertEquals(content, goal2.getContent());
        assertEquals(isComplete, goal2.isComplete());
        assertEquals(sortOrder, goal2.getSortOrder());
        assertEquals(date, goal2.getDate());

        // Test with null content
        DatedGoal nullContentGoal = new DatedGoal(2, null, true, 1, Context.HOME, date);
        assertNull(nullContentGoal.getContent());

        // Test with negative sortOrder
        DatedGoal negativeSortOrderGoal = new DatedGoal(3, "Negative Sort Order", false, -1, Context.HOME, date);
        assertEquals(-1, negativeSortOrderGoal.getSortOrder());

        // Test with zero sortOrder
        DatedGoal zeroSortOrderGoal = new DatedGoal(4, "Zero Sort Order", true, 0, Context.HOME, date);
        assertEquals(0, zeroSortOrderGoal.getSortOrder());

        // Test with None context
        DatedGoal noneContextGoal = new DatedGoal(5, "None Context", true, 0, Context.NONE, date);
        assertEquals(Context.NONE, noneContextGoal.getContext());

        // Test with maximum integer sortOrder
        int maxIntSortOrder = Integer.MAX_VALUE;
        DatedGoal maxIntSortOrderGoal = new DatedGoal(5, "Max Int Sort Order", false,
                maxIntSortOrder, Context.HOME, date);
        assertEquals(maxIntSortOrder, maxIntSortOrderGoal.getSortOrder());

        // Test with with leap year date
        Calendar calendar3 = new GregorianCalendar(2024, Calendar.FEBRUARY, 29);
        Date leapYearDate = new Date(calendar3);
        DatedGoal leapYearGoal = new DatedGoal(6, "Leap Year Date", false,
                maxIntSortOrder, Context.HOME, leapYearDate);
        assertEquals(leapYearDate, leapYearGoal.getDate());

        // Test with different instances
        DatedGoal sameAttributesGoal = new DatedGoal(id, content, isComplete, sortOrder, context, date);
        DatedGoal differentIdGoal = new DatedGoal(2, content, isComplete, sortOrder, context, date);
        DatedGoal differentContentGoal = new DatedGoal(id, "Different Content", isComplete,
                sortOrder, context, date);
        DatedGoal differentIsCompleteGoal = new DatedGoal(id, content, true, sortOrder, context, date);
        DatedGoal differentSortOrderGoal = new DatedGoal(id, content, isComplete, 1, context, date);
        DatedGoal differentContextGoal = new DatedGoal(id, content, isComplete, sortOrder, Context.WORK, date);
        DatedGoal differentDateGoal = new DatedGoal(id, content, isComplete, sortOrder, Context.WORK, new Date(calendar2));

        assertEquals(goal, sameAttributesGoal);
        assertNotEquals(goal, differentIdGoal);
        assertNotEquals(goal, differentContentGoal);
        assertNotEquals(goal, differentIsCompleteGoal);
        assertNotEquals(goal, differentSortOrderGoal);
        assertNotEquals(goal, differentContextGoal);
        assertNotEquals(goal, differentDateGoal);
    }

    @Test
    public void testEqualsAndHashCode()
    {   Date date = new Date(Calendar.getInstance());
        DatedGoal goal1 = new DatedGoal(1, "Goal 1", true, 1, Context.HOME, date);
        DatedGoal goal2 = new DatedGoal(1, "Goal 1", true, 1, Context.HOME, date);
        DatedGoal goal3 = new DatedGoal(2, "Goal 2", false, 2, Context.HOME, new Date(Calendar.getInstance()));

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

    @Test
    public void testComparable()
    {
        Date date = new Date(Calendar.getInstance());
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        Date date1 = new Date(calendar);
        Comparable goal1 = new DatedGoal(1, "Goal 1", true, 1, Context.HOME,date);
        Comparable goal2 = new DatedGoal(1, "Goal 1", true, 1, Context.HOME, date);
        Comparable goal3 = new DatedGoal(2, "Goal 2", false, 2, Context.WORK, date1); // Reflexive

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
