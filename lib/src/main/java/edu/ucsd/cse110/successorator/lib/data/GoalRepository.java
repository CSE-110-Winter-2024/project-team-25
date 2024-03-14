package edu.ucsd.cse110.successorator.lib.data;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface GoalRepository {
    void toggleIsCompleteStatus(int id);
    void rollOver();
    int addGoal(String content);
    int addGoal(Goal goal);
    void deleteGoal(int id);
    Subject<List<Goal>> getAllGoalsAsSubject();
    List<Goal> getAllGoals();
    Goal find(int id);
    void deleteRecurringGoalWithDateByRecurrenceID(int id);
    }
