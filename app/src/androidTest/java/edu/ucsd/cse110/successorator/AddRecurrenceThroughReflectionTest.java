package edu.ucsd.cse110.successorator;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory;

@RunWith(AndroidJUnit4.class)
public class AddRecurrenceThroughReflectionTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddRecurrenceThroughReflection() {
        // 1. Get MainActivity Instance
        activityRule.getScenario().onActivity(activity -> {
            try {
                // 2. Get MainViewModel via Reflection
                Field viewModelField = MainActivity.class.getDeclaredField("activityModel");
                viewModelField.setAccessible(true);
                MainViewModel viewModel = (MainViewModel) viewModelField.get(activity);

                // 3. Get GoalRepository via Reflection
                Field goalRepositoryField = MainViewModel.class.getDeclaredField("goalRepository");
                goalRepositoryField.setAccessible(true);
                GoalRepository goalRepository = (GoalRepository) goalRepositoryField.get(viewModel);

                // 4. Create Recurrence Object
                Calendar calendar = new GregorianCalendar(2023, Calendar.NOVEMBER, 20); // Replace with desired start date
                Date startDate = new Date(calendar);
                Recurrence recurrence = RecurrenceFactory.createWeeklyRecurrence(startDate);

                // 5. Get addRecurrence Method (assuming it exists in GoalRepository)
                Method addRecurrenceMethod = GoalRepository.class.getDeclaredMethod("addRecurrence", Recurrence.class);
                addRecurrenceMethod.setAccessible(true);

                // 6. Invoke addRecurrence
                addRecurrenceMethod.invoke(goalRepository, recurrence);

            } catch (Exception e) {
                // Handle exceptions from reflection here
                e.printStackTrace();
            }
        });
    }
}