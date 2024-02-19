package edu.ucsd.cse110.successorator;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Subject;

@RunWith(AndroidJUnit4.class)
public class CreateGoalEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private void createGoal(String goalText) {
        Espresso.onView(withId(R.id.edit_bar_menu_add_goal)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.goal_content)).perform(ViewActions.typeText(goalText));
        Espresso.onView(withId(android.R.id.button1)).perform(ViewActions.click()); // Assuming positive button has android default id
    }

    private void deleteFirstGoal() {
        // 1. Get MainActivity Instance
        activityRule.getScenario().onActivity(activity -> {
            try {
                // 2. Get MainViewModel via Reflection
                Field viewModelField = MainActivity.class.getDeclaredField("activityModel");
                viewModelField.setAccessible(true);
                MainViewModel viewModel = (MainViewModel) viewModelField.get(activity);

                // 3. Get deleteGoal Method
                Method deleteGoalMethod = MainViewModel.class.getDeclaredMethod("deleteGoal", int.class);
                deleteGoalMethod.setAccessible(true);

                // 4. Find goal ID (Need some mechanism)
                Field goalListField = MainViewModel.class.getDeclaredField("orderedGoals");
                goalListField.setAccessible(true); // Grant access to private fields
                int goalId = ((Subject<List<Goal>>) goalListField.get(viewModel)).getValue().get(0).getId();

                // 5. Invoke deleteGoal
                deleteGoalMethod.invoke(viewModel, goalId);

            } catch (Exception e) {
                // Handle exceptions from reflection here
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testCreateGoal_EmptyText() {
        createGoal("");
        onData(anything())
                .inAdapterView(withId(R.id.goal_list))
                .atPosition(0)
                .onChildView(withId(R.id.goal_content_text))
                .check(matches(withText("")));
        deleteFirstGoal();
    }

    @Test
    public void testCreateGoal_ValidText() {
        String goalText = "Complete unit tests";
        createGoal(goalText);
        onData(anything())
                .inAdapterView(withId(R.id.goal_list))
                .atPosition(0)
                .onChildView(withId(R.id.goal_content_text))
                .check(matches(withText(goalText)));
        deleteFirstGoal();
    }

    @Test
    public void testCreateGoal_NumericInput() {
        String goalText = "12345";
        createGoal(goalText);
        onData(anything())
                .inAdapterView(withId(R.id.goal_list))
                .atPosition(0)
                .onChildView(withId(R.id.goal_content_text))
                .check(matches(withText(goalText)));
        deleteFirstGoal();
    }

    @Test
    public void testCreateGoal_SpecialCharacters() {
        String goalText = "@#$%^&*()";
        createGoal(goalText);
        Espresso.onView(withText(goalText)).check(matches(isDisplayed()));
        deleteFirstGoal();
    }
}
