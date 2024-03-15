package edu.ucsd.cse110.successorator;

import org.junit.Test;

import static org.junit.Assert.*;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalType;
import edu.ucsd.cse110.successorator.lib.domain.Type;
import edu.ucsd.cse110.successorator.lib.util.GoalBuilder;

public class GoalBuilderTest {


    @Test
    public void testSetId() {
        GoalBuilder builder = new GoalBuilder();
        Integer id = 1;
        builder.setId(id);
        assertEquals(id, builder.getId());
    }

    @Test
    public void testSetContent() {
        GoalBuilder builder = new GoalBuilder();
        String content = "Test content";
        builder.setContent(content);
        assertEquals(content, builder.getContent());
    }

    @Test
    public void testSetComplete() {
        GoalBuilder builder = new GoalBuilder();
        builder.setComplete(true);
        assertTrue(builder.isComplete());
    }

    @Test
    public void testSetSortOrder() {
        GoalBuilder builder = new GoalBuilder();
        int sortOrder = 5;
        builder.setSortOrder(sortOrder);
        assertEquals(sortOrder, builder.getSortOrder());
    }

    @Test
    public void testSetContest() {
        GoalBuilder builder = new GoalBuilder();
        builder.setContext(Context.HOME);
        assertEquals(Context.HOME, builder.getContext());
    }



}
