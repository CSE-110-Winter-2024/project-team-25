package edu.ucsd.cse110.successorator.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;


public class RoomGoalRepository implements GoalRepository {
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
        var newGoalEntity = new GoalEntity(content, false, 0);
        return goalDao.append(newGoalEntity);
    }
    public Subject<List<Goal>> getAllCompleteGoals(){
        var EntityLiveList = goalDao.getAllCompleteGoalEntitiesAsLiveData();
        assert(EntityLiveList.getValue()!=null);
        var goalLiveList = Transformations.map(EntityLiveList, entities ->{
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(goalLiveList);
    }
    public Subject<List<Goal>> getAllUncompleteGoals(){
        var EntityLiveList = goalDao.getAllUncompleteGoalEntitiesAsLiveData();
        assert(EntityLiveList.getValue()!=null);
        var goalLiveList = Transformations.map(EntityLiveList, entities ->{
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(goalLiveList);
    }
    /*
    * this method is for testing
    * */
    public Goal find(int id){
        return goalDao.find(id).toGoal();
    }
    /*
     * this method is for testing
     * */

    public GoalDao getGoalDao(){
        return goalDao;
    }
    /*
     * this method is for testing
     * */
    public List<Goal> getAllGoal(){
        List<GoalEntity> uncompleteList = goalDao.getAllUncompleteGoalEntities();
        List<GoalEntity> completeList = goalDao.getAllCompleteGoalEntities();
        List<Goal> allGoalList= Stream.concat(completeList.stream(), uncompleteList.stream())
                .map(GoalEntity::toGoal)
                .collect(Collectors.toList());
        return allGoalList;
    }
}
