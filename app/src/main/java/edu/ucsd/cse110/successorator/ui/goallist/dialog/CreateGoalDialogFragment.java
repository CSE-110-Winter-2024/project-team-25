package edu.ucsd.cse110.successorator.ui.goallist.dialog;

import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createDailyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createMonthlyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createWeeklyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createYearlyRecurrence;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import android.widget.RadioButton;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.db.GoalEntity;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

public class CreateGoalDialogFragment extends DialogFragment {
    private FragmentDialogCreateGoalBinding view;
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
        this.view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());

        Calendar calendar = Calendar.getInstance();
        String weeklyTime = new SimpleDateFormat("EE").format(calendar.getTime());
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int index = (dayOfMonth - 1) / 7 + 1;
        String word;
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

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var content = view.goalContent.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, MainActivity.DELAY_HOUR);
        Date today = new Date(calendar);
//        GoalEntity goalEntity = new GoalEntity(content, false, 0);
//        Goal goal = goalEntity.toGoal();
        Goal goal = new DatedGoal(0, content, false, 0, today);
        if (view.oneTimeRecurrenceButton.isChecked()) {
            activityModel.addGoal(goal);
        }
        else if (view.dailyRecurrenceButton.isChecked()) {
            Recurrence recurrence = createDailyRecurrence(today);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
            activityModel.addGoal(recurringGoal);

        }
        else if(view.weeklyRecurrenceButton.isChecked()){
            Recurrence recurrence = createWeeklyRecurrence(today);
            RecurringGoal recurringGoal = new RecurringGoal(goal, recurrence);
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

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

}