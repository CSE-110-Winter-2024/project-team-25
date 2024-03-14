package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecurringGoalWithDate extends DatedGoal{
    private final int recurrenceID;
    public RecurringGoalWithDate(
            @NonNull Integer id,
            @NonNull String content,
            @NonNull boolean isComplete,
            @Nullable int sortOrder,
            @NonNull Context context,
            @NonNull Date date,
            @NonNull int recurrenceID) {
        super(id, content, isComplete, sortOrder, context, date);
        this.recurrenceID = recurrenceID;
    }

    public int getRecurrenceID(){
        return recurrenceID;
    }

    public RecurringGoalWithDate(
            @NonNull Integer id,
            @NonNull String content,
            @NonNull boolean isComplete,
            @Nullable int sortOrder,
            @NonNull Date date,
            @NonNull int recurrenceID) {
        super(id, content, isComplete, sortOrder, date);
        this.recurrenceID = recurrenceID;
    }

    public RecurringGoalWithDate(@NonNull Goal goal,
                     Date date, @NonNull int recurrenceID) {
        super(goal.getId(), goal.getContent(), goal.isComplete(), goal.getSortOrder(), date);
        this.recurrenceID = recurrenceID;
    }
    public boolean equals(RecurringGoalWithDate goal){
        return this.recurrenceID == goal.getRecurrenceID()&&super.getDate().equals(goal.getDate());
    }

}
