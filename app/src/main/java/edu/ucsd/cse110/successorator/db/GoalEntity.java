package edu.ucsd.cse110.successorator.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

@Entity (tableName = "goal")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "content")
    public String content;
    @ColumnInfo(name = "isComplete")
    public boolean isComplete;
    @ColumnInfo(name = "sortOrder")
    public int sortOrder;
    public GoalEntity(String content, boolean isComplete, int sortOrder){
        this.content = content;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
    }
    public static GoalEntity fromGoal(Goal goal){
        return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder());
    }
    public Goal toGoal(){
        return new Goal(id, content, isComplete, sortOrder);
    }
}
