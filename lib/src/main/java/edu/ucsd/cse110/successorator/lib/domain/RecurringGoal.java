package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecurringGoal extends Goal {
    private Recurrence recurrence;

    public RecurringGoal(
        @NonNull Integer id,
        @NonNull String content,
        @NonNull boolean isComplete,
        @Nullable int sortOrder,
        @NonNull Context context,
        @NonNull Recurrence recurrence
    ) {
        super(id, content, isComplete, sortOrder, context);
        this.recurrence = recurrence;
    }
    public RecurringGoal(
            @NonNull Integer id,
            @NonNull String content,
            @NonNull boolean isComplete,
            @Nullable int sortOrder,
            @NonNull Recurrence recurrence
    ) {
        super(id, content, isComplete, sortOrder);
        this.recurrence = recurrence;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }
}
