package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;


import org.junit.Test;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.PendingGoal;

public class US1PendingGoalTest {
//    @Test
//    public void testConstructorAndGetters()
//    {
//        int id = 1;
//        String content = "Example Goal";
//        boolean isComplete = false;
//        int sortOrder = 0;
//        Context context = Context.HOME;
//        boolean deleted = false;
//
//        //Test Constructor 1
//        PendingGoal goal = new PendingGoal(id, content, isComplete, sortOrder, context, deleted);
//
//        assertEquals(id, goal.getId());
//        assertEquals(content, goal.getContent());
//        assertEquals(isComplete, goal.isComplete());
//        assertEquals(sortOrder, goal.getSortOrder());
//        assertEquals(context, goal.getContext());
//        assertEquals(deleted, goal.isDeleted());
//
//        //Test Constructor 2
//        PendingGoal goal1 = new PendingGoal(id, content, isComplete, sortOrder, deleted);
//
//        assertNotEquals(goal, goal1);
//
//        assertEquals(id, goal1.getId());
//        assertEquals(content, goal1.getContent());
//        assertEquals(isComplete, goal1.isComplete());
//        assertEquals(sortOrder, goal1.getSortOrder());
//        assertEquals(deleted, goal1.isDeleted());
//
//        //Test Constructor 3
//        Goal simpleGoal = new Goal(id, content, isComplete, sortOrder, context);
//        PendingGoal goal2 = new PendingGoal(simpleGoal, deleted);
//
//        assertNotEquals(goal, goal2);
//        assertEquals(goal1, goal2);
//
//        assertEquals(id, goal2.getId());
//        assertEquals(content, goal2.getContent());
//        assertEquals(isComplete, goal2.isComplete());
//        assertEquals(sortOrder, goal2.getSortOrder());
//        assertEquals(deleted, goal2.isDeleted());
//
//        // Test with null content
//        PendingGoal nullContentGoal = new PendingGoal(2, null, true, 1, Context.HOME, deleted);
//        assertNull(nullContentGoal.getContent());
//
//        // Test with negative sortOrder
//        PendingGoal negativeSortOrderGoal = new PendingGoal(3, "Negative Sort Order", false, -1, Context.HOME, deleted);
//        assertEquals(-1, negativeSortOrderGoal.getSortOrder());
//
//        // Test with zero sortOrder
//        PendingGoal zeroSortOrderGoal = new PendingGoal(4, "Zero Sort Order", true, 0, Context.HOME, deleted);
//        assertEquals(0, zeroSortOrderGoal.getSortOrder());
//
//        // Test with None context
//        PendingGoal noneContextGoal = new PendingGoal(5, "None Context", true, 0, Context.NONE, deleted);
//        assertEquals(Context.NONE, noneContextGoal.getContext());
//
//        // Test with maximum integer sortOrder
//        int maxIntSortOrder = Integer.MAX_VALUE;
//        PendingGoal maxIntSortOrderGoal = new PendingGoal(5, "Max Int Sort Order", false,
//                maxIntSortOrder, Context.HOME, deleted);
//        assertEquals(maxIntSortOrder, maxIntSortOrderGoal.getSortOrder());
//
//        // Test with different instances
//        PendingGoal sameAttributesGoal = new PendingGoal(id, content, isComplete, sortOrder, context, deleted);
//        PendingGoal differentIdGoal = new PendingGoal(2, content, isComplete, sortOrder, context, deleted);
//        PendingGoal differentContentGoal = new PendingGoal(id, "Different Content", isComplete,
//                sortOrder, context, deleted);
//        PendingGoal differentIsCompleteGoal = new PendingGoal(id, content, true, sortOrder, context, deleted);
//        PendingGoal differentSortOrderGoal = new PendingGoal(id, content, isComplete, 1, context, deleted);
//        PendingGoal differentContextGoal = new PendingGoal(id, content, isComplete, sortOrder, Context.WORK, deleted);
//        PendingGoal differentDeletedGoal = new PendingGoal(id, content, isComplete, sortOrder, Context.WORK, true);
//
//        assertEquals(goal, sameAttributesGoal);
//        assertNotEquals(goal, differentIdGoal);
//        assertNotEquals(goal, differentContentGoal);
//        assertNotEquals(goal, differentIsCompleteGoal);
//        assertNotEquals(goal, differentSortOrderGoal);
//        assertNotEquals(goal, differentContextGoal);
//        assertNotEquals(goal, differentDeletedGoal);
//    }
//
//    @Test
//    public void testToDateGoal() {
//        PendingGoal goal = new PendingGoal(0, "content", true, 1, Context.HOME, false);
//        Date date = new Date(Calendar.getInstance());
//        DatedGoal datedGoal = goal.toDatedGoal(date);
//        DatedGoal datedGoal1 = new DatedGoal(0, "content", true, 1, Context.HOME, date);
//
//        assertEquals(datedGoal, datedGoal1);
//    }
//
//    @Test
//    public void testSetMethod() {
//        PendingGoal goal = new PendingGoal(1, "Goal 1", true, 1, Context.HOME, false);
//        PendingGoal tempGoal = new PendingGoal(1, "Goal 1", true, 1, Context.HOME, false);
//        goal.setDeleted(true);
//        assertEquals(true, goal.isDeleted());
//        assertNotEquals(goal, tempGoal);
//    }
//
//    @Test
//    public void testEqualsAndHashCode()
//    {
//        PendingGoal goal1 = new PendingGoal(1, "Goal 1", true, 1, Context.HOME, false);
//        PendingGoal goal2 = new PendingGoal(1, "Goal 1", true, 1, Context.HOME, false);
//        PendingGoal goal3 = new PendingGoal(2, "Goal 2", false, 2, Context.HOME, false);
//
//        // Reflexive
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

//    @Test
//    public void testComparable()
//    {
//        Comparable goal1 = new Goal(1, "Goal 1", true, 1, Context.HOME, false);
//        Comparable goal2 = new Goal(1, "Goal 1", true, 1, Context.HOME, false);
//        Comparable goal3 = new Goal(2, "Goal 2", false, 2, Context.WORK, true);// Reflexive
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
