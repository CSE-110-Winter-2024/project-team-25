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
import edu.ucsd.cse110.successorator.lib.domain.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.PendingGoal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;


public class CreateGoalDialogFragment extends DialogFragment {
    private FragmentDialogCreateGoalBinding view;
    private FragmentDialogCreateRecurringGoalBinding recurringView;
    private FragmentDialogCreatePendingGoalBinding pendingView;
    private MainViewModel activityModel;

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
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (activityModel.getAdapterIndexAsSubject().getValue() == 0) {
            return creatTodayDialog();
        }
        else if (activityModel.getAdapterIndexAsSubject().getValue() == 1) {
            return creatTomorrowDialog();
        }
        else if (activityModel.getAdapterIndexAsSubject().getValue() == 2) {
            return creatRecurringDialog();

        }
        else if (activityModel.getAdapterIndexAsSubject().getValue() == 3){
            return creatPendingDialog();
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        if (activityModel.getAdapterIndexAsSubject().getValue() == 0) {
            addTodayGoal(dialog, which);
        }
        else if (activityModel.getAdapterIndexAsSubject().getValue() == 1){
            addTomorrowGoal(dialog, which);
        }
        else if (activityModel.getAdapterIndexAsSubject().getValue() == 2){
            addRecurringGoal(dialog, which);
        }
        else if (activityModel.getAdapterIndexAsSubject().getValue() == 3){
            addPendingGoal(dialog, which);
        }
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

    private Dialog creatTodayDialog() {
        this.view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());
        Calendar calendar = activityModel.getToday().getCalendar();
        String weeklyTime = new SimpleDateFormat("EE").format(calendar.getTime());
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int index = (dayOfMonth - 1) / 7 + 1;
        String word;
        // handle the wording of date
        switch (index) {
            case 1:
                word = "st";
                break;
            case 2:
                word = "nd";
                break;
            case 3:
                word = "rd";
                break;
            default:
                word = "th";
                break;
        }
        String yearlyTime = new SimpleDateFormat("MM/dd").format(calendar.getTime());


        view.weeklyRecurrenceButton.setText("weekly on "+ weeklyTime);
        view.monthlyRecurrenceButton.setText("monthly on "+ index + word + " " + weeklyTime);
        view.yearlyRecurrenceButton.setText("yearly on "+ yearlyTime);

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please provide the description for the new Goal.")
                .setView(view.getRoot())
                .setPositiveButton("Save", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }
    private Dialog creatTomorrowDialog() {
        this.view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());
        Calendar calendar = activityModel.getTomorrow().getCalendar();
        String weeklyTime = new SimpleDateFormat("EE").format(calendar.getTime());
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int index = (dayOfMonth - 1) / 7 + 1;
        String word;
        // handle the wording of date
        switch (index) {
            case 1:
                word = "st";
                break;
            case 2:
                word = "nd";
                break;
            case 3:
                word = "rd";
                break;
            default:
                word = "th";
                break;
        }
        String yearlyTime = new SimpleDateFormat("MM/dd").format(calendar.getTime());


        view.weeklyRecurrenceButton.setText("weekly on "+ weeklyTime);
        view.monthlyRecurrenceButton.setText("monthly on "+ index + word + " " + weeklyTime);
        view.yearlyRecurrenceButton.setText("yearly on "+ yearlyTime);

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please provide the description for the new Goal.")
                .setView(view.getRoot())
                .setPositiveButton("Save", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private Dialog creatRecurringDialog() {
        this.recurringView = FragmentDialogCreateRecurringGoalBinding.inflate(getLayoutInflater());
        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please provide the description for the new Goal.")
                .setView(recurringView.getRoot())
                .setPositiveButton("Save", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private Dialog creatPendingDialog() {
        this.pendingView = FragmentDialogCreatePendingGoalBinding.inflate(getLayoutInflater());
        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please provide the description for the new Goal.")
                .setView(pendingView.getRoot())
                .setPositiveButton("Save", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }
    private void addTodayGoal(DialogInterface dialog, int which) {
        var content = view.goalContent.getText().toString();
        Date today = activityModel.getTargetDate();
        Goal goal = new DatedGoal(0, content, false, 0, today);
        if (view.oneTimeRecurrenceButton.isChecked()) {
            activityModel.addGoal(goal);
        }
        else if (view.dailyRecurrenceButton.isChecked()) {
            Recurrence recurrence = createDailyRecurrence(today);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence, false);
            activityModel.addGoal(recurringGoal);

        }
        else if(view.weeklyRecurrenceButton.isChecked()){
            Recurrence recurrence = createWeeklyRecurrence(today);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence, false);
            activityModel.addGoal(recurringGoal);
        }
        else if(view.monthlyRecurrenceButton.isChecked()) {
            Recurrence recurrence = createMonthlyRecurrence(today);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
            activityModel.addGoal(recurringGoal);
        }
        else if(view.yearlyRecurrenceButton.isChecked()) {
            Recurrence recurrence = createYearlyRecurrence(today);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
            activityModel.addGoal(recurringGoal);
        }
        else {
            throw new IllegalStateException("No recurrence options is selected.");
        }
        dialog.dismiss();
    }
    private void addTomorrowGoal(DialogInterface dialog, int which) {
        var content = view.goalContent.getText().toString();
        Date tomorrow = activityModel.getTomorrow();
        Goal goal = new DatedGoal(0, content, false, 0, tomorrow);
        if (view.oneTimeRecurrenceButton.isChecked()) {
            activityModel.addGoal(goal);
        }
        else if (view.dailyRecurrenceButton.isChecked()) {
            Recurrence recurrence = createDailyRecurrence(tomorrow);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence, false);
            activityModel.addGoal(recurringGoal);

        }
        else if(view.weeklyRecurrenceButton.isChecked()){
            Recurrence recurrence = createWeeklyRecurrence(tomorrow);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence, false);
            activityModel.addGoal(recurringGoal);
        }
        else if(view.monthlyRecurrenceButton.isChecked()) {
            Recurrence recurrence = createMonthlyRecurrence(tomorrow);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
            activityModel.addGoal(recurringGoal);
        }
        else if(view.yearlyRecurrenceButton.isChecked()) {
            Recurrence recurrence = createYearlyRecurrence(tomorrow);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
            activityModel.addGoal(recurringGoal);
        }
        else {
            throw new IllegalStateException("No recurrence options is selected.");
        }
        dialog.dismiss();
    }
    private void addRecurringGoal(DialogInterface dialog, int which) {
        var content = recurringView.goalContent.getText().toString();
        var dateString = recurringView.recurrenceDate.getText().toString();
        String[] parts = dateString.split("/");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Date format is not correct!");
        }
        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            Date setDate = getDate(year, month, day);
            Log.d("error in setDate",year + ", " + month + ", " + + day);
            Goal goal = new DatedGoal(0, content, false, 0, setDate);
            if (recurringView.dailyButton.isChecked()) {
                Recurrence recurrence = createDailyRecurrence(setDate);
                RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
                activityModel.addGoal(recurringGoal);

            }
            else if(recurringView.weeklyButton.isChecked()){
                Recurrence recurrence = createWeeklyRecurrence(setDate);
                RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
                activityModel.addGoal(recurringGoal);
            }
            else if(recurringView.monthlyButton.isChecked()) {
                Recurrence recurrence = createMonthlyRecurrence(setDate);
                RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
                activityModel.addGoal(recurringGoal);
            }
            else if(recurringView.yearlyButton.isChecked()) {
                Recurrence recurrence = createYearlyRecurrence(setDate);
                RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
                activityModel.addGoal(recurringGoal);
            }
            else {
                throw new IllegalStateException("No recurrence options is selected.");
            }
            dialog.dismiss();
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException("Date format is not correct!");
        }
    }

    @NonNull
    private Date getDate(int year, int month, int day) {
        if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
            throw new IllegalArgumentException("Please enter a valid date!");
        }

        // Additional checks for specific month-day combinations
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                throw new IllegalArgumentException("Please enter a valid date!");
            }
        }
        else if (month == 2) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                if (day > 29) {
                    throw new IllegalArgumentException("Please enter a valid date!");
                }
            } else {
                if (day > 28) {
                    throw new IllegalArgumentException("Please enter a valid date!");
                }
            }
        }
        Date today = activityModel.getToday();
        Calendar calendar = today.getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        Date setDate = new Date(calendar);
        return setDate;
    }

    private void addPendingGoal(DialogInterface dialog, int which) {
        var content = pendingView.goalContent.getText().toString();
        Goal goal = new PendingGoal(0, content, false, 0, false);
        activityModel.addGoal(goal);
        dialog.dismiss();
    }
}