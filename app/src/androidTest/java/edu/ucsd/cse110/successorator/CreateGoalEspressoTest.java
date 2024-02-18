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

@RunWith(AndroidJUnit4.class)
public class CreateGoalEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private void createGoal(String goalText) {
        Espresso.onView(withId(R.id.create_goal_button)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.content_edit_text)).perform(ViewActions.typeText(goalText));
        Espresso.onView(withId(android.R.id.button1)).perform(ViewActions.click()); // Assuming positive button has android default id
    }

    private void deleteFirstGoals() {
        onData(anything())
                .inAdapterView(withId(R.id.goal_list))
                .atPosition(0)
                .onChildView(withId(R.id.goal_delete_button))
                .perform(ViewActions.click());
    }

    @Test
    public void testCreateGoal_EmptyText() {
        createGoal("");
        onData(anything())
                .inAdapterView(withId(R.id.goal_list))
                .atPosition(0)
                .onChildView(withId(R.id.goal_content_text))
                .check(matches(withText("")));
        deleteFirstGoals();
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
        deleteFirstGoals();
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
        deleteFirstGoals();
    }

    @Test
    public void testCreateGoal_SpecialCharacters() {
        String goalText = "@#$%^&*()";
        createGoal(goalText);
        Espresso.onView(withText(goalText)).check(matches(isDisplayed()));
        deleteFirstGoals();
    }
}
