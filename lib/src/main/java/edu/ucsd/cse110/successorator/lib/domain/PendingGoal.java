package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.Objects;

public class PendingGoal extends Goal {
    private boolean finished;
    public PendingGoal(
        Integer id,
        String content,
        boolean isComplete,
        int sortOrder,
        Context context,
        boolean finished
    ) {
        super(id, content, isComplete, sortOrder, context);
        this.finished = finished;
    }
    public PendingGoal(
            Integer id,
            String content,
            boolean isComplete,
            int sortOrder,
            boolean finished
    ) {
        super(id, content, isComplete, sortOrder);
        this.finished = finished;
    }
    public PendingGoal(@NonNull Goal goal,
                       boolean finished) {
        super(goal.getId(), goal.getContent(), goal.isComplete(), goal.getSortOrder());
        this.finished = finished;
    }
    public DatedGoal toDatedGoal(Date date) {
        this.finished = true;
        return new DatedGoal(
                this.getId(),
                this.getContent(),
                this.isComplete(),
                this.getSortOrder(),
                this.getContext(),
                date
        );
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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
                this.isFinished() == goal.isFinished();

    }
}
