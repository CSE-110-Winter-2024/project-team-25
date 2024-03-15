package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.Objects;

public class PendingGoal extends Goal {
    private boolean deleted;
    public PendingGoal(
        Integer id,
        String content,
        boolean isComplete,
        int sortOrder,
        Context context,
        boolean deleted
    ) {
        super(id, content, isComplete, sortOrder, context);
        this.deleted = deleted;
    }
    public PendingGoal(
            Integer id,
            String content,
            boolean isComplete,
            int sortOrder,
            boolean deleted
    ) {
        super(id, content, isComplete, sortOrder);
        this.deleted = deleted;
    }
    public PendingGoal(@NonNull Goal goal,
                       boolean deleted) {
        super(goal.getId(), goal.getContent(), goal.isComplete(), goal.getSortOrder(), goal.getContext());
        this.deleted = deleted;
    }
    public DatedGoal toDatedGoal(Date date) {
        this.deleted = true;
        return new DatedGoal(
                this.getId(),
                this.getContent(),
                this.isComplete(),
                this.getSortOrder(),
                this.getContext(),
                date
        );
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PendingGoal goal = (PendingGoal) o;
        return this.isComplete() == goal.isComplete() &&
                this.getSortOrder() == goal.getSortOrder() && Objects.equals(this.id(), goal.id()) &&
                Objects.equals(this.getContent(), goal.getContent())
                && Objects.equals(this.getContext(), goal.getContext()) &&
                this.isDeleted() == goal.isDeleted();

    }
}
