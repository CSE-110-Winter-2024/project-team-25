package edu.ucsd.cse110.successorator.db;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.PendingGoal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalWithDate;

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
    public Recurrence recurrence; //create a table
    @ColumnInfo(name = "deleted")
    public Boolean deleted;
    @ColumnInfo(name = "RecurrenceID")
    public Integer RecurrenceID;
    public GoalEntity(String content, boolean isComplete, int sortOrder, Long date,
                      Recurrence recurrence, Boolean deleted, Integer RecurrenceID){
        this.content = content;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
        this.date = date;
        this.recurrence = recurrence;
        this.deleted = deleted;
        this.RecurrenceID = RecurrenceID;
    }

    @Ignore
    public GoalEntity(String content, boolean isComplete, int sortOrder){
        this.content = content;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
    }
    public static GoalEntity fromGoal(Goal goal) {
        if(goal instanceof RecurringGoal){
            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(),
                    null, ((RecurringGoal) goal).getRecurrence(), null, null);
        } else if(goal instanceof PendingGoal){
            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(),
                    null, null, ((PendingGoal) goal).isDeleted(), null);
        } else if(goal instanceof DatedGoal){
            if(goal instanceof RecurringGoalWithDate){
                return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(),
                        ((DatedGoal) goal).getDate().getCalendar().getTimeInMillis(),
                        null, null, ((RecurringGoalWithDate)goal).getRecurrenceID());
            }
            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(),
                    ((DatedGoal) goal).getDate().getCalendar().getTimeInMillis(),
                    null, null, null);
        }


        return null;
    }
    public Goal toGoal() {
        if(this.date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(this.date);
            if(this.RecurrenceID!=null){
                return new RecurringGoalWithDate(id, content, isComplete, sortOrder,
                        new edu.ucsd.cse110.successorator.lib.domain.Date(calendar), RecurrenceID);
            }
            return new DatedGoal(id, content, isComplete, sortOrder, new edu.ucsd.cse110.successorator.lib.domain.Date(calendar));
        } else if(this.recurrence != null){
            return new RecurringGoal(id, content, isComplete, sortOrder, recurrence);
        } else if(this.deleted != null){
            return new PendingGoal(id, content, isComplete, sortOrder, deleted);
        }
        return new Goal(id, content, isComplete, sortOrder);
    }
}
