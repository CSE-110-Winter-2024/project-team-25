package edu.ucsd.cse110.successorator.ui.goallist.dialog;

import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createDailyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createMonthlyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createWeeklyRecurrence;
import static edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory.createYearlyRecurrence;

import android.app.AlertDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Context;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.Type;
import edu.ucsd.cse110.successorator.lib.util.DateFormatter;

public class ViewLogicHandler {

    public static DateFormatter formatter = new DateFormatter();

        public static Dialog handleViewLogic(int adapterIndex, FragmentDialogCreateGoalBinding view,
                                                   AlertDialog.Builder builder, Date targetDate) {
            switch (adapterIndex) {
                case 0:
                    handleWeeklyMonthlyYearlyRecurrence(view, targetDate);
                case 1:
                    handleWeeklyMonthlyYearlyRecurrence(view, targetDate);
                    break;
                case 2:
                    handleDefaultRecurrence(view);
                    break;
                case 3:
                    handlePendingGoalRecurrence(view);
                    break;
            }

            if (adapterIndex == 0 || adapterIndex == 1 || adapterIndex == 3) {
                hideStartOnAndRecurrenceDate(view);
            }

            return builder.setView(view.getRoot()).create();
        }

        private static void handleWeeklyMonthlyYearlyRecurrence(FragmentDialogCreateGoalBinding view,
                                                                Date targetDate) {
            Calendar calendar = targetDate.getCalendar();
            view.startOnLabel.setVisibility(View.INVISIBLE);
            view.recurrenceDate.setVisibility(View.INVISIBLE);
            view.weeklyRecurrenceButton.setText(formatter.getWeeklyRecurringText(calendar));
            view.monthlyRecurrenceButton.setText(formatter.getMonthlyRecurringText(calendar));
            view.yearlyRecurrenceButton.setText(formatter.getYearlyRecurringText(calendar));
        }

        private static void handleDefaultRecurrence(FragmentDialogCreateGoalBinding view) {
            view.weeklyRecurrenceButton.setText(R.string.weekly_recurrence);
            view.monthlyRecurrenceButton.setText(R.string.monthly_recurrence);
            view.yearlyRecurrenceButton.setText(R.string.yearly_recurrence);
            view.oneTimeRecurrenceButton.setVisibility(View.INVISIBLE);
        }

        private static void handlePendingGoalRecurrence(FragmentDialogCreateGoalBinding view) {
            view.dailyRecurrenceButton.setVisibility(View.INVISIBLE);
            view.oneTimeRecurrenceButton.setVisibility(View.INVISIBLE);
            view.weeklyRecurrenceButton.setVisibility(View.INVISIBLE);
            view.monthlyRecurrenceButton.setVisibility(View.INVISIBLE);
            view.yearlyRecurrenceButton.setVisibility(View.INVISIBLE);
        }

        private static void hideStartOnAndRecurrenceDate(FragmentDialogCreateGoalBinding view) {
            view.startOnLabel.setVisibility(View.INVISIBLE);
            view.recurrenceDate.setVisibility(View.INVISIBLE);
        }

    public static Boolean checkDeleteClick(Type type){
        if(type == Type.PENDING){
            return false;
        }
        return null;
    }

    public static Recurrence checkRecurrenceClick(Date date, FragmentDialogCreateGoalBinding view){
        if(date == null){
            return null;
        }
        Recurrence recurrence = null;
        if (view.dailyRecurrenceButton.isChecked()) {
            recurrence = createDailyRecurrence(date);
        }
        else if(view.weeklyRecurrenceButton.isChecked()){
            recurrence = createWeeklyRecurrence(date);
        }
        else if(view.monthlyRecurrenceButton.isChecked()) {
            recurrence = createMonthlyRecurrence(date);
        }
        else if(view.yearlyRecurrenceButton.isChecked()) {
            recurrence = createYearlyRecurrence(date);
        }
        return recurrence;
    }
    public static Context checkContextClick(FragmentDialogCreateGoalBinding view){
        Context context;
        if (view.HomeContextButton.isChecked()){
            context = Context.HOME;
        } else if (view.WorkContextButton.isChecked()){
            context = Context.WORK;
        } else if (view.SchoolContextButton.isChecked()){
            context = Context.SCHOOL;
        } else if (view.ErrandContextButton.isChecked()){
            context = Context.ERRANDS;
        } else {
            throw new IllegalStateException("No context options is selected.");
        }
        Log.d("refractor check", " "+context);
        return context;
    }
    public static Long checkDateClick(Type type, Date targetDate, FragmentDialogCreateGoalBinding view){
        if((type == Type.TOMORROW || type == Type.TODAY) && view.oneTimeRecurrenceButton.isChecked()){
            Log.d("refractor check", " "+targetDate.getCalendar().getTimeInMillis() );
            return targetDate.getCalendar().getTimeInMillis();
        }
        return null;
    }

    public static Date checkDateForRecurrence(Type type, Date targetDate, FragmentDialogCreateGoalBinding view){
        Date startDateForRecurrence;
        if(type == Type.RECURRENCE){
            startDateForRecurrence = parse(view.recurrenceDate.getText().toString());
        }else if (type == Type.TODAY || type == Type.TOMORROW){
            startDateForRecurrence = targetDate;
        }else{
            startDateForRecurrence = null;
        }
        if(startDateForRecurrence!=null){
            Log.d("refractor check", "startDateForRecurrence "+startDateForRecurrence.toString());
        }
        return startDateForRecurrence;
    }

    @NonNull
    private static Date parse(String dateString) {
        String[] parts = dateString.split("/");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DATE, day);
        Date setDate = new Date(calendar);
        return setDate;
    }
}
