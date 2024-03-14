package edu.ucsd.cse110.successorator.db;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalType;
import edu.ucsd.cse110.successorator.lib.domain.PendingGoal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalWithDate;
import edu.ucsd.cse110.successorator.lib.util.GoalBuilder;

@Entity (tableName = "goal")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "content")
    public String content;
    @ColumnInfo(name = "context")
    public Context context;
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
                      Recurrence recurrence, Boolean deleted, Integer RecurrenceID, Context context){
        this.content = content;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
        this.date = date;
        this.recurrence = recurrence;
        this.deleted = deleted;
        this.RecurrenceID = RecurrenceID;
        this.context = context;
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
                    null, ((RecurringGoal) goal).getRecurrence(), null, null, goal.getContext());
        } else if(goal instanceof PendingGoal){
            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(),
                    null, null, ((PendingGoal) goal).isDeleted(), null, goal.getContext());
        } else if(goal instanceof DatedGoal){
            if(goal instanceof RecurringGoalWithDate){
                return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(),
                        ((DatedGoal) goal).getDate().getCalendar().getTimeInMillis(),
                        null, null, ((RecurringGoalWithDate)goal).getRecurrenceID(), goal.getContext());
            }
            return new GoalEntity(goal.getContent(), goal.isComplete(), goal.getSortOrder(),
                    ((DatedGoal) goal).getDate().getCalendar().getTimeInMillis(),
                    null, null, null, goal.getContext());
        }


        return null;
    }
    // Old version without using GoalBuilder
//    public Goal toGoal() {
//        if(this.date != null) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(this.date);
//            if(this.RecurrenceID!=null){
//                return new RecurringGoalWithDate(id, content, isComplete, sortOrder,
//                        new edu.ucsd.cse110.successorator.lib.domain.Date(calendar), RecurrenceID);
//            }
//            return new DatedGoal(id, content, isComplete, sortOrder, new edu.ucsd.cse110.successorator.lib.domain.Date(calendar));
//        } else if(this.recurrence != null){
//            return new RecurringGoal(id, content, isComplete, sortOrder, recurrence);
//        } else if(this.deleted != null){
//            return new PendingGoal(id, content, isComplete, sortOrder, deleted);
//        }
//        return new GoalBuilder()
//          .setId(id)
//          .setContent(content)
//          .setComplete(isComplete)
//          .setSortOrder(sortOrder)
//          .setContext(context)
//          .build();
//    }

    // New version using GoalBuilder
    public Goal toGoal() {
        GoalBuilder goalBuilder = new GoalBuilder()
                                      .setId(id)
                                      .setContent(content)
                                      .setComplete(isComplete)
                                      .setSortOrder(sortOrder)
                                      .setContext(context);
//                                      .setDate(date)
//                .setRecurrence(recurrence)
//                .setDeleted(deleted);

        if(this.date != null) {
            goalBuilder.setDate(this.date);
            if(this.RecurrenceID!=null){
                Log.i("goalType", GoalType.RecurringGoalWithDate.toString());
                goalBuilder.setGoalType(GoalType.RecurringGoalWithDate);
                goalBuilder.setRecurrenceID(this.RecurrenceID);
            }
            Log.i("goalType", GoalType.DatedGoal.toString());
            goalBuilder.setGoalType(GoalType.DatedGoal);
        } else if(this.recurrence != null){
            Log.i("goalType", GoalType.RecurringGoal.toString());
            goalBuilder.setGoalType(GoalType.RecurringGoal);
            goalBuilder.setRecurrence(this.recurrence);
        } else if(this.deleted != null){
            Log.i("goalType", GoalType.PendingGoal.toString());
            goalBuilder.setGoalType(GoalType.PendingGoal);
            goalBuilder.setDeleted(this.deleted);
        }
        Context context1 = goalBuilder.getContext();
        Log.i("GoalEntityContext", goalBuilder.getContent() +
          (context1 != null ? context1.toString() : goalBuilder.getContent() + "NULL"));
        return goalBuilder.build();
    }
}
