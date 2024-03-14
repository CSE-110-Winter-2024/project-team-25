package edu.ucsd.cse110.successorator.lib.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalType;
import edu.ucsd.cse110.successorator.lib.domain.PendingGoal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalWithDate;

public class GoalBuilder {
  private Integer id;
  private String content;
  private boolean isComplete;
  private int sortOrder;
  private Context context;
  private GoalType goalType;
  private Long date;
  private Recurrence recurrence;
  private Boolean deleted;
  private Integer recurrenceID;

  public GoalBuilder() {
  }

  public GoalBuilder setGoalType(@NotNull GoalType goalType) {
    this.goalType = goalType;
    return this;
  }

  public GoalBuilder setId(@Nullable Integer id) {
    this.id = id;
    return this;
  }

  public GoalBuilder setContent(@Nullable String content) {
    this.content = content;
    return this;
  }

  public GoalBuilder setComplete(boolean isComplete) {
    this.isComplete = isComplete;
    return this;
  }

  public GoalBuilder setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
    return this;
  }

  public GoalBuilder setContext(@NonNull Context context) {
    this.context = context;
    return this;
  }

  public GoalBuilder setDate(@NotNull Long date){
    this.date = date;
    return this;
  }

  public GoalBuilder setRecurrence (@NonNull Recurrence recurrence){
    this.recurrence = recurrence;
    return this;
  }

  public GoalBuilder setDeleted (@NonNull Boolean deleted){
    this.deleted = deleted;
    return this;
  }

  public GoalBuilder setRecurrenceID (@NonNull Integer recurrenceID){
    this.recurrenceID = recurrenceID;
    return this;
  }

  public Integer getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public boolean isComplete() {
    return isComplete;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public Context getContext() {
    return context;
  }

  public GoalType getGoalType() {
    return goalType;
  }

  public Long getDate() {
    return date;
  }

  public Recurrence getRecurrence() {
    return recurrence;
  }

  public Boolean isDeleted() {
    return deleted;
  }

  public Integer getRecurrenceID() {
    return recurrenceID;
  }


  public Goal build() {
    if (id == null || content == null) {
      throw new IllegalStateException("Id and content must not be null");
    }
    Goal goal = new Goal(id, content, isComplete, sortOrder, context);
    Calendar calendar = Calendar.getInstance();


    switch (goalType){
      case Goal:
        return goal;
      case RecurringGoalWithDate:
        calendar.setTimeInMillis(this.date);
        return new RecurringGoalWithDate(goal, new Date(calendar), recurrenceID);
      case DatedGoal:
        calendar.setTimeInMillis(this.date);
        return new DatedGoal(goal, new Date(calendar));
      case RecurringGoal:
        return new RecurringGoal(goal, this.recurrence);
      case PendingGoal:
        return new PendingGoal(goal, deleted);
      default:
        throw new IllegalStateException("Unexpected value: " + goalType);
    }
  }
}
