package edu.ucsd.cse110.successorator.ui.goallist.dialog;

import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createDailyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createMonthlyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createWeeklyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createYearlyRecurrence;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreatePendingGoalBinding;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateRecurringGoalBinding;

import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Date;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.PendingGoal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.Type;
import edu.ucsd.cse110.successorator.lib.util.DateFormatter;
import edu.ucsd.cse110.successorator.lib.util.GoalBuilder;


public class CreateGoalDialogFragment extends DialogFragment {
    private FragmentDialogCreateGoalBinding view;
    private FragmentDialogCreateRecurringGoalBinding recurringView;
    private FragmentDialogCreatePendingGoalBinding pendingView;
    private MainViewModel activityModel;

    private GoalBuilder goalBuilder;


    CreateGoalDialogFragment() {}
    public static CreateGoalDialogFragment newInstance() {
        var fragment = new CreateGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
        this.goalBuilder = new GoalBuilder();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please provide the description for the new Goal.")
                .setPositiveButton("Save", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick);
        return ViewLogicHandler.handleViewLogic(activityModel.getAdapterIndexAsSubject().getValue(),
                view, builder, activityModel.getTargetDate());
    }
    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        Goal newGoal = goalBuilder
                .setId(1)
                .setContent(view.goalContent.getText().toString())
                .setComplete(false)
                .setSortOrder(0)
                .setContext(ViewLogicHandler.checkContextClick(view))
                .setDate(ViewLogicHandler.checkDateClick(activityModel.getType(),
                        activityModel.getTargetDate(), view))
                .setDeleted(ViewLogicHandler.checkDeleteClick(activityModel.getType()))
                .setRecurrence(ViewLogicHandler.checkRecurrenceClick(ViewLogicHandler.checkDateForRecurrence(activityModel.getType(),
                                                        activityModel.getTargetDate(), view), view))
                .build();
        activityModel.addGoal(newGoal);
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

//    private Boolean checkDeleteClick(Type type){
//        if(type == Type.PENDING){
//            return false;
//        }
//        return null;
//    }
//
//    private Recurrence checkRecurrenceClick(Date date){
//        if(date == null){
//            return null;
//        }
//        Recurrence recurrence;
//        if (view.dailyRecurrenceButton.isChecked()) {
//            recurrence = createDailyRecurrence(date);
//        }
//        else if(view.weeklyRecurrenceButton.isChecked()){
//            recurrence = createWeeklyRecurrence(date);
//        }
//        else if(view.monthlyRecurrenceButton.isChecked()) {
//            recurrence = createMonthlyRecurrence(date);
//        }
//        else if(view.yearlyRecurrenceButton.isChecked()) {
//            recurrence = createYearlyRecurrence(date);
//        }
//        else {
//            throw new IllegalStateException("No recurrence options is selected.");
//        }
//        return recurrence;
//    }
//    private Context checkContextClick(){
//        Context context;
//        if (view.HomeContextButton.isChecked()){
//            context = Context.HOME;
//        } else if (view.WorkContextButton.isChecked()){
//            context = Context.WORK;
//        } else if (view.SchoolContextButton.isChecked()){
//            context = Context.SCHOOL;
//        } else if (view.ErrandContextButton.isChecked()){
//            context = Context.ERRANDS;
//        } else {
//            throw new IllegalStateException("No context options is selected.");
//        }
//        return context;
//    }
//    private Long checkDateClick(Type type){
//        if((type == Type.TOMORROW || type == Type.TODAY) && view.oneTimeRecurrenceButton.isChecked()){
//            return activityModel.getTargetDate().getCalendar().getTimeInMillis();
//        }
//        return null;
//    }
//
//    private Date checkDateForRecurrence(Type type, Date targetDate){
//        Date startDateForRecurrence = new Date(Calendar.getInstance());
//        if(type == Type.RECURRENCE){
//            startDateForRecurrence = parse(view.recurrenceDate.getText().toString());
//        }else if (type == Type.TODAY || type == Type.TOMORROW){
//            startDateForRecurrence = targetDate;
//        }else{
//            startDateForRecurrence = null;
//        }
//        return startDateForRecurrence;
//    }
//
//    @NonNull
//    private Date parse(String dateString) {
//        String[] parts = dateString.split("/");
//        int year = Integer.parseInt(parts[0]);
//        int month = Integer.parseInt(parts[1]);
//        int day = Integer.parseInt(parts[2]);
//        if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
//            throw new IllegalArgumentException("Please enter a valid date!");
//        }
//
//        // Additional checks for specific month-day combinations
//        if (month == 4 || month == 6 || month == 9 || month == 11) {
//            if (day > 30) {
//                throw new IllegalArgumentException("Please enter a valid date!");
//            }
//        }
//        else if (month == 2) {
//            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
//                if (day > 29) {
//                    throw new IllegalArgumentException("Please enter a valid date!");
//                }
//            } else {
//                if (day > 28) {
//                    throw new IllegalArgumentException("Please enter a valid date!");
//                }
//            }
//        }
//        Date today = activityModel.getToday();
//        Calendar calendar = today.getCalendar();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month-1);
//        calendar.set(Calendar.DATE, day);
//        Date setDate = new Date(calendar);
//        return setDate;
//    }
//    private void addTodayGoal(DialogInterface dialog, int which) {
//        var content = view.goalContent.getText().toString();
//        Date today = activityModel.getTargetDate();
//        Context context;
//
//        if (view.HomeContextButton.isChecked()){
//            context = Context.HOME;
//        } else if (view.WorkContextButton.isChecked()){
//            context = Context.WORK;
//        } else if (view.SchoolContextButton.isChecked()){
//            context = Context.SCHOOL;
//        } else if (view.ErrandContextButton.isChecked()){
//            context = Context.ERRANDS;
//        } else {
//            throw new IllegalStateException("No context options is selected.");
//        }
//        Log.i("debugging", context.toString());
//
//        Goal goal = new DatedGoal(0, content, false, 0, today, context);
//        if (view.oneTimeRecurrenceButton.isChecked()) {
//            activityModel.addGoal(goal);
//        }
//        else if (view.dailyRecurrenceButton.isChecked()) {
//            Recurrence recurrence = createDailyRecurrence(today);
//            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence, false);
//            activityModel.addGoal(recurringGoal);
//
//        }
//        else if(view.weeklyRecurrenceButton.isChecked()){
//            Recurrence recurrence = createWeeklyRecurrence(today);
//            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence, false);
//            activityModel.addGoal(recurringGoal);
//        }
//        else if(view.monthlyRecurrenceButton.isChecked()) {
//            Recurrence recurrence = createMonthlyRecurrence(today);
//            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
//            activityModel.addGoal(recurringGoal);
//        }
//        else if(view.yearlyRecurrenceButton.isChecked()) {
//            Recurrence recurrence = createYearlyRecurrence(today);
//            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
//            activityModel.addGoal(recurringGoal);
//        }
//        else {
//            throw new IllegalStateException("No recurrence options is selected.");
//        }
//
//        Log.i("CGDFragment Context", goal.getContent() + (goal.getContext() != null ?
//          goal.getContext().toString() : "NULL"));
//
//        dialog.dismiss();
//    }
//    private void addTomorrowGoal(DialogInterface dialog, int which) {
//        var content = view.goalContent.getText().toString();
//        Date tomorrow = activityModel.getTomorrow();
//        Goal goal = new DatedGoal(0, content, false, 0, tomorrow);
//        if (view.oneTimeRecurrenceButton.isChecked()) {
//            activityModel.addGoal(goal);
//        }
//        else if (view.dailyRecurrenceButton.isChecked()) {
//            Recurrence recurrence = createDailyRecurrence(tomorrow);
//            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence, false);
//            activityModel.addGoal(recurringGoal);
//
//        }
//        else if(view.weeklyRecurrenceButton.isChecked()){
//            Recurrence recurrence = createWeeklyRecurrence(tomorrow);
//            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence, false);
//            activityModel.addGoal(recurringGoal);
//        }
//        else if(view.monthlyRecurrenceButton.isChecked()) {
//            Recurrence recurrence = createMonthlyRecurrence(tomorrow);
//            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
//            activityModel.addGoal(recurringGoal);
//        }
//        else if(view.yearlyRecurrenceButton.isChecked()) {
//            Recurrence recurrence = createYearlyRecurrence(tomorrow);
//            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
//            activityModel.addGoal(recurringGoal);
//        }
//        else {
//            throw new IllegalStateException("No recurrence options is selected.");
//        }
//        dialog.dismiss();
//    }
//    private void addRecurringGoal(DialogInterface dialog, int which) {
//        var content = recurringView.goalContent.getText().toString();
//        var dateString = recurringView.recurrenceDate.getText().toString();
//        String[] parts = dateString.split("/");
//        if (parts.length != 3) {
//            throw new IllegalArgumentException("Date format is not correct!");
//        }
//        try {
//            int year = Integer.parseInt(parts[0]);
//            int month = Integer.parseInt(parts[1]);
//            int day = Integer.parseInt(parts[2]);
//
//            Date setDate = getDate(year, month, day);
//            Log.d("error in setDate",setDate.getCalendar().get(Calendar.YEAR)+" "+ setDate.getCalendar().get(Calendar.MONTH)+setDate.getCalendar().get(Calendar.DAY_OF_MONTH));
//            Goal goal = new DatedGoal(0, content, false, 0, setDate);
//            if (recurringView.dailyButton.isChecked()) {
//                Recurrence recurrence = createDailyRecurrence(setDate);
//                RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
//                activityModel.addGoal(recurringGoal);
//
//            }
//            else if(recurringView.weeklyButton.isChecked()){
//                Recurrence recurrence = createWeeklyRecurrence(setDate);
//                RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
//                activityModel.addGoal(recurringGoal);
//            }
//            else if(recurringView.monthlyButton.isChecked()) {
//                Recurrence recurrence = createMonthlyRecurrence(setDate);
//                RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
//                activityModel.addGoal(recurringGoal);
//            }
//            else if(recurringView.yearlyButton.isChecked()) {
//                Recurrence recurrence = createYearlyRecurrence(setDate);
//                RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
//                activityModel.addGoal(recurringGoal);
//            }
//            else {
//                throw new IllegalStateException("No recurrence options is selected.");
//            }
//            dialog.dismiss();
//        }
//        catch (NumberFormatException e) {
//            throw new NumberFormatException("Date format is not correct!");
//        }
//    }
//
//    @NonNull
//    private Date getDate(int year, int month, int day) {
//        if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
//            throw new IllegalArgumentException("Please enter a valid date!");
//        }
//
//        // Additional checks for specific month-day combinations
//        if (month == 4 || month == 6 || month == 9 || month == 11) {
//            if (day > 30) {
//                throw new IllegalArgumentException("Please enter a valid date!");
//            }
//        }
//        else if (month == 2) {
//            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
//                if (day > 29) {
//                    throw new IllegalArgumentException("Please enter a valid date!");
//                }
//            } else {
//                if (day > 28) {
//                    throw new IllegalArgumentException("Please enter a valid date!");
//                }
//            }
//        }
//        Date today = activityModel.getToday();
//        Calendar calendar = today.getCalendar();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month-1);
//        calendar.set(Calendar.DATE, day);
//        Date setDate = new Date(calendar);
//        return setDate;
//    }
//
//    private void addPendingGoal(DialogInterface dialog, int which) {
//        var content = pendingView.goalContent.getText().toString();
//        Goal goal = new PendingGoal(0, content, false, 0, false);
//        activityModel.addGoal(goal);
//        dialog.dismiss();
//    }
}