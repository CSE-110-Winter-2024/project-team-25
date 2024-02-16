package edu.ucsd.cse110.successorator.lib.data;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface GoalRepository {
    void changeIsCompleteStatus(int id, boolean isComplete);
    void rollOver();
    int addGoal(String content);
    Subject<List<Goal>> getAllCompleteGoals();
    Subject<List<Goal>> getAllUncompleteGoals();
}
