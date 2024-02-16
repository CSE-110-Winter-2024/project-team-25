package edu.ucsd.cse110.successorator;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.ucsd.cse110.successorator.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class US1GoalTest
{
  @Test
  public void testConstructorAndGetters() {
    int id = 1;
    String content = "Example Goal";
    boolean isComplete = false;
    int sortOrder = 0;

    Goal goal = new Goal(id, content, isComplete, sortOrder);

    assertEquals(id, goal.getId());
    assertEquals(content, goal.getContent());
    assertEquals(isComplete, goal.isComplete());
    assertEquals(sortOrder, goal.getSortOrder());

    // Test with null content
    Goal nullContentGoal = new Goal(2, null, true, 1);
    assertNull(nullContentGoal.getContent());

    // Test with negative sortOrder
    Goal negativeSortOrderGoal = new Goal(3, "Negative Sort Order", false, -1);
    assertEquals(-1, negativeSortOrderGoal.getSortOrder());

    // Test with zero sortOrder
    Goal zeroSortOrderGoal = new Goal(4, "Zero Sort Order", true, 0);
    assertEquals(0, zeroSortOrderGoal.getSortOrder());

    // Test with maximum integer sortOrder
    int maxIntSortOrder = Integer.MAX_VALUE;
    Goal maxIntSortOrderGoal = new Goal(5, "Max Int Sort Order", false, maxIntSortOrder);
    assertEquals(maxIntSortOrder, maxIntSortOrderGoal.getSortOrder());

    // Test with different instances
    Goal sameAttributesGoal = new Goal(id, content, isComplete, sortOrder);
    Goal differentIdGoal = new Goal(2, content, isComplete, sortOrder);
    Goal differentContentGoal = new Goal(id, "Different Content", isComplete, sortOrder);
    Goal differentIsCompleteGoal = new Goal(id, content, true, sortOrder);
    Goal differentSortOrderGoal = new Goal(id, content, isComplete, 1);

    assertEquals(goal, sameAttributesGoal);
    assertNotEquals(goal, differentIdGoal);
    assertNotEquals(goal, differentContentGoal);
    assertNotEquals(goal, differentIsCompleteGoal);
    assertNotEquals(goal, differentSortOrderGoal);
  }

  @Test
  public void testEqualsAndHashCode() {
    Goal goal1 = new Goal(1, "Goal 1", true, 1);
    Goal goal2 = new Goal(1, "Goal 1", true, 1);
    Goal goal3 = new Goal(2, "Goal 2", false, 2);

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

}
