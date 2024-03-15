package edu.ucsd.cse110.successorator;

import org.junit.Test;

import static org.junit.Assert.*;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class US8NewCompareToForGoalTest
{
  @Test
  public void compareContext()
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
