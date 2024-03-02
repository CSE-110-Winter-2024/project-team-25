package edu.ucsd.cse110.successorator.lib.domain;

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
}
