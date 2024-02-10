package edu.ucsd.cse110.successorator.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.data.goalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;

public class RoomGoalRepository implements goalRepository {
    private final GoalDao goalDao;
    public RoomGoalRepository(GoalDao goalDao){
        this.goalDao = goalDao;
    }
    public void changeIsCompleteStatus(int id, boolean isComplete){
        goalDao.updateGoalStatus(id, isComplete);
    }
    public void rollOver(){
        goalDao.rollOver();
    }
    public int addGoal(String content){
        var newGoalEntity = new GoalEntity(content, true, 0);
        return goalDao.append(newGoalEntity);
    }
    public Subject<List<Goal>> getAllCompleteGoals(){
        var EntityLiveList = goalDao.getAllCompleteGoalEntitiesAsLiveData();
        var goalLiveList = Transformations.map(EntityLiveList, entities ->{
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(goalLiveList);
    }
    public Subject<List<Goal>> getAllUncompleteGoals(){
        var EntityLiveList = goalDao.getAllUncompleteGoalEntitiesAsLiveData();
        var goalLiveList = Transformations.map(EntityLiveList, entities ->{
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(goalLiveList);
    }
}
