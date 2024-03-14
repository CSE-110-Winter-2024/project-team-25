package edu.ucsd.cse110.successorator.db;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Calendar;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

@Dao
public interface GoalDao {
    @Query("SELECT * FROM goal WHERE id = :id")
    GoalEntity find(int id);
    @Query("SELECT * FROM goal WHERE isComplete = 1 AND recurrence IS NULL ORDER BY sortOrder")
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
    @Query("DELETE FROM goal WHERE RecurrenceID = :id")
    void deleteRecurringGoalWithDateByRecurrenceID(int id);
    @Delete
    void deleteEntities(List<GoalEntity> entities);
    @Delete
    void deleteEntity(GoalEntity entity);
    @Transaction
    default int append(GoalEntity entity){
        Integer maxSortOrder = getMaxSortOrder();
        GoalEntity newGoalEntity;
        if(maxSortOrder.equals(null)){
            newGoalEntity = new GoalEntity(entity.content, entity.isComplete, 0, entity.date,
                    entity.recurrence, entity.deleted, entity.RecurrenceID);
        }else{
            newGoalEntity = new GoalEntity(entity.content, entity.isComplete,
                    maxSortOrder+1, entity.date, entity.recurrence, entity.deleted, entity.RecurrenceID);
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
