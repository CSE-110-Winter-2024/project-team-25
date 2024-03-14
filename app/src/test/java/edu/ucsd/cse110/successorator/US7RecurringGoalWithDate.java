package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class US7RecurringGoalWithDate {
    @Test
    public void compare()
    {

        int id = 1;
        String content = "Example Goal";
        boolean isComplete = false;
        int sortOrder = 0;
        Context context = Context.HOME;

        Goal goalHome = new Goal(id, content, isComplete, sortOrder, Context.HOME);
        Goal goalWork = new Goal(id, content, isComplete, sortOrder, Context.WORK);

        int compareResult = goalHome.compareTo(goalWork);
        System.out.println("HOME".compareTo("WORK"));
        assertTrue(!(compareResult > 0));
    }
}
