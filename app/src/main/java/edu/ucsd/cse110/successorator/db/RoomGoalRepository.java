package edu.ucsd.cse110.successorator.db;

import android.util.Log;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;


public class RoomGoalRepository implements GoalRepository {
    protected final GoalDao goalDao;

    public RoomGoalRepository(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    public void toggleIsCompleteStatus(int id) {
        var goalEntity = goalDao.find(id);
        if(goalEntity != null) goalDao.updateGoalStatus(id, !goalEntity.toGoal().isComplete());
    }
    public void updateIsFinish(int id, Recurrence recurrence, boolean isFinish){
        goalDao.updateGoalIsFinishStatus(id, recurrence, isFinish);
    }

    public void rollOver() {
        goalDao.rollOver();
    }

    public int addGoal(String content) {
        var newGoalEntity = new GoalEntity(content, false, 0);
        return goalDao.append(newGoalEntity);
    }
    public int addGoal(Goal goal) {
        var newGoalEntity = GoalEntity.fromGoal(goal);
        return goalDao.append(newGoalEntity);
    }


    public void deleteGoal(int id) {
        var goalEntity = goalDao.find(id);
        if(goalEntity != null) goalDao.deleteEntity(goalEntity);
    }

    public Subject<List<Goal>> getAllGoalsAsSubject() {
        var entityLiveList = goalDao.getAllGoalEntitiesAsLiveData();
        if(entityLiveList == null) return new LiveDataSubjectAdapter<>(null);
        var goalLiveList = Transformations.map(entityLiveList,  entities -> {

            return entities.stream()
                        .map(GoalEntity::toGoal)
                        .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(goalLiveList);
    }
    public List<Goal> getAllGoals() {
        return goalDao.getAllGoalEntities().stream().map(GoalEntity::toGoal).collect(Collectors.toList());
    }
    public List<Goal> getAllGoalsForTest() {
        return goalDao.getAllGoalEntities().stream().map(GoalEntity::toGoal).collect(Collectors.toList());
    }
}
