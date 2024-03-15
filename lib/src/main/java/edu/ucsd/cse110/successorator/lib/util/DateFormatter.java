package edu.ucsd.cse110.successorator.lib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatter {
    private String pattern = "EEEE, MM/dd";
    private String pattern_yearly = "MM/dd";
    private String pattern_weekly = "EE";
    SimpleDateFormat formatter;
    public DateFormatter(){
        formatter = new SimpleDateFormat(pattern);
    }
    public String getFormatDate(Calendar calendar){
        return formatter.format(calendar.getTime());
    }
    public String getMonthlyRecurringText(Calendar calendar){
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String weeklyTime = new SimpleDateFormat("EE").format(calendar.getTime());
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
        return "monthly on "+ index + word + " " + weeklyTime;
    }

    public String getWeeklyRecurringText(Calendar calendar){
        String weeklyTime = new SimpleDateFormat("EE").format(calendar.getTime());
        return "weekly on "+ weeklyTime;
    }
    public String getYearlyRecurringText(Calendar calendar){
        String yearlyTime = new SimpleDateFormat("MM/dd").format(calendar.getTime());
        return "yearly on "+ yearlyTime;
    }
    //public String
}