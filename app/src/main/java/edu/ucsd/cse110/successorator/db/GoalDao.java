package edu.ucsd.cse110.successorator.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface GoalDao {
    @Query("SELECT * FROM goal WHERE id = :id")
    GoalEntity find(int id);
    @Query("SELECT * FROM goal WHERE isComplete = 1 ORDER BY sortOrder")
    List<GoalEntity> getAllCompleteGoalEntities();
    @Query("SELECT * FROM goal ORDER BY sortOrder")
    List<GoalEntity> getAllGoalEntities();
    @Query("SELECT * FROM goal ORDER BY sortOrder")
    LiveData<List<GoalEntity>> getAllGoalEntitiesAsLiveData();
    @Query("UPDATE goal SET isComplete = :status WHERE id = :id")
    void updateGoalStatus(int id, boolean status);
    @Query("SELECT MAX(sortOrder) FROM goal")
    int getMaxSortOrder();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addGoalEntity(GoalEntity goal);
    @Delete
    void deleteEntities(List<GoalEntity> entities);
    @Delete
    void deleteEntity(GoalEntity entity);
    @Transaction
    default int append(GoalEntity entity){
        Integer maxSortOrder = getMaxSortOrder();
        GoalEntity newGoalEntity;
        if(maxSortOrder.equals(null)){
            newGoalEntity = new GoalEntity(entity.content, entity.isComplete, 0);
        }else{
            newGoalEntity = new GoalEntity(entity.content, entity.isComplete, maxSortOrder+1);
        }
        Long id = addGoalEntity(newGoalEntity);
        return Math.toIntExact(id);
    }
    @Transaction
    default void rollOver(){
        List<GoalEntity> removedGoalEntity = getAllCompleteGoalEntities();
        deleteEntities(removedGoalEntity);
    }
}
