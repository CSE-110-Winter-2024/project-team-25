package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class RecurringGoal extends Goal {
    private Recurrence recurrence;
    private boolean isFinished;

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
            @NonNull Context context,
            @NonNull Recurrence recurrence,
            @NonNull boolean isFinished
    ) {
        super(id, content, isComplete, sortOrder, context);
        this.recurrence = recurrence;
        this.isFinished = isFinished;
    }
    public RecurringGoal(@NonNull Goal goal,
                         @NonNull Recurrence recurrence) {
        super(goal.getId(), goal.getContent(), goal.isComplete(), goal.getSortOrder());
        this.recurrence = recurrence;
    }
    public RecurringGoal(@NonNull Goal goal,
                         @NonNull Recurrence recurrence,
                         @NonNull boolean isFinished) {
        super(goal.getId(), goal.getContent(), goal.isComplete(), goal.getSortOrder());
        this.recurrence = recurrence;
        this.isFinished = isFinished;
    }

    public RecurringGoal(
            @NonNull Integer id,
            @NonNull String content,
            @NonNull boolean isComplete,
            @Nullable int sortOrder,
            @NonNull Recurrence recurrence,
            @NonNull boolean isFinished
    ) {
        super(id, content, isComplete, sortOrder);
        this.recurrence = recurrence;
        this.isFinished = isFinished;
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
    public boolean getIsFinished(){
        return isFinished;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurringGoal goal = (RecurringGoal) o;
        return this.isComplete() == goal.isComplete() &&
                this.getSortOrder() == goal.getSortOrder() && Objects.equals(this.id(), goal.id()) &&
                Objects.equals(this.getContent(), goal.getContent())
                && Objects.equals(this.getContext(), goal.getContext()) &&
                Objects.equals(this.getRecurrence(), goal.getRecurrence());

    }
}
