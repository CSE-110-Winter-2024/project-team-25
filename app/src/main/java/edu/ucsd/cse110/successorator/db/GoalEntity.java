package edu.ucsd.cse110.successorator.db;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.PendingGoal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

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
    @ColumnInfo(name = "date")
    public Long date;
    @ColumnInfo(name = "recurrence")
    public Recurrence recurrence;
    @ColumnInfo(name = "deleted")
    public Boolean deleted;

    @ColumnInfo(name = "isFinished")
    public Boolean isFinished;

//    public GoalEntity(String content, boolean isComplete, int sortOrder, Date date, Recurrence recurrence, Boolean deleted){
//        this.content = content;
//        this.isComplete = isComplete;
//        this.sortOrder = sortOrder;
//        this.date = date != null ? date.getCalendar().getTimeInMillis() : null;
//        this.recurrence = recurrence;
//        this.deleted = deleted;
//    }
    public GoalEntity(String content, boolean isComplete, int sortOrder, Long date, Recurrence recurrence, Boolean deleted){
        this.content = content;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
        this.date = date;
        this.recurrence = recurrence;
        this.deleted = deleted;
        this.isFinished = false;
    }

    @Ignore
    public GoalEntity(String content, boolean isComplete, int sortOrder){
        this.content = content;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
    }
    public static GoalEntity fromGoal(Goal goal) {
        if(goal instanceof RecurringGoal){
            Log.d("reached this line", ((RecurringGoal) goal).getRecurrence().toString());
            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(), null, ((RecurringGoal) goal).getRecurrence(), null);
        } else if(goal instanceof PendingGoal){
            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(), null, null, ((PendingGoal) goal).isDeleted());
        } else{
            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(), ((DatedGoal) goal).getDate().getCalendar().getTimeInMillis(), null, null);
        }
//        }else if(goal instanceof DatedGoal){
//            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(), ((DatedGoal) goal).getDate().getCalendar().getTimeInMillis(), null, null);
//        }
//        return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder());
    }
    public Goal toGoal() {
        if(this.date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(this.date);
            return new DatedGoal(id, content, isComplete, sortOrder, new edu.ucsd.cse110.successorator.lib.domain.Date(calendar));
        } else if(this.recurrence != null){
            return new RecurringGoal(id, content, isComplete, sortOrder, recurrence, isFinished);
        } else if(this.deleted != null){
            return new PendingGoal(id, content, isComplete, sortOrder, deleted);
        }
        return new Goal(id, content, isComplete, sortOrder);
    }
}
