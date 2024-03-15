package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Goal implements Comparable<Goal> {
    private final @Nullable Integer id;
    private final @Nullable String content;
    private final @Nullable boolean isComplete;
    private final @Nullable int sortOrder;
    private  @Nullable Context context;

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
    public Goal(
            @NotNull Integer id,
            @NotNull String content,
            @NotNull boolean isComplete,
            @Nullable int sortOrder,
            @Nullable Context context
    ) {
        this.id = id;
        this.content = content;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
        this.context = context;
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
          Objects.equals(content, goal.content) && Objects.equals(context, goal.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, isComplete, sortOrder);
    }

    @Override
    public int compareTo(@NonNull Goal goal) {
        if(this.context == null){
            return 1;
        }
        if(goal.context == null){
            return -1;
        }
        if (this.context != goal.context)
            return this.context.compareTo(goal.context);
        else if(this.isComplete != goal.isComplete)
            return Boolean.compare(this.isComplete, goal.isComplete);
        return Integer.compare(this.sortOrder, goal.sortOrder);
    }

    @Override
    public String toString()
    {
        return "Goal{" + "id=" + id + ", content='" + content + '\''
          + ", isComplete=" + isComplete + ", sortOrder=" + sortOrder
          + ", context=" + (context != null ? context.toString() : "NULL") + '}';
    }

    @NonNull
    public Context getContext() {
//        Log.i("Benny", context.toString());
//        System.out.println(context != null ? context.toString() : "NULL");
        return context;
    }

    public void setContext(@NonNull Context context) {
        this.context = context;
    }


}
