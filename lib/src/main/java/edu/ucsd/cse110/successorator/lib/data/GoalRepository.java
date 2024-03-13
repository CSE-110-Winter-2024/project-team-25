package edu.ucsd.cse110.successorator.lib.data;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface GoalRepository {
    void toggleIsCompleteStatus(int id);
    void rollOver();
    int addGoal(String content);
    int addGoal(Goal goal);
    void deleteGoal(int id);
    void updateGoal(int id, Function<Goal, Goal> update);
    Subject<List<Goal>> getAllGoals();
}
