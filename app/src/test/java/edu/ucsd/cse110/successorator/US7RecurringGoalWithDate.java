package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
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
}
