package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatedGoal extends Goal {
    private Date date;

    public DatedGoal(
        @NonNull Integer id,
        @NonNull String content,
        @NonNull boolean isComplete,
        @Nullable int sortOrder,
        @NonNull Context context,
        @NonNull Date date
    ) {
        super(id, content, isComplete, sortOrder, context);
        this.date = date;
    }
    public DatedGoal(
            @NonNull Integer id,
            @NonNull String content,
            @NonNull boolean isComplete,
            @Nullable int sortOrder,
            @NonNull Date date
    ) {
        super(id, content, isComplete, sortOrder);
        this.date = date;
    }
    public DatedGoal(@NonNull Goal goal,
                       boolean deleted) {
        super(goal.getId(), goal.getContent(), goal.isComplete(), goal.getSortOrder());
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
