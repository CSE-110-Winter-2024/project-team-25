package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Just a dummy domain model that does nothing in particular. Delete me.
 */
public class Goal {
    private final @Nullable Integer id;
    private final @Nullable String content;
    private final @Nullable boolean isComplete;

    private final @Nullable int sortOrder;
    public Goal(
        @NotNull Integer id,
        @NotNull String content,
        @NotNull boolean isComplete,
        @Nullable int sortOrder
    ) {
        this.id = id;
        this.content = content;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
    }


    @Nullable
    public int getId() {
        return id;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    @Nullable
    public Integer id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return isComplete == goal.isComplete &&
          sortOrder == goal.sortOrder && Objects.equals(id, goal.id) &&
          Objects.equals(content, goal.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, isComplete, sortOrder);
    }
}
