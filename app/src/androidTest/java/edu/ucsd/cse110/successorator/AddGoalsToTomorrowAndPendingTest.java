package edu.ucsd.cse110.successorator;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddGoalsToTomorrowAndPendingTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private void createGoal(String goalText) {
        onView(withId(R.id.edit_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.goal_content)).perform(typeText(goalText));
        onView(withId(android.R.id.button1)).perform(click()); // Assuming positive button has android default id
    }

    private void selectView(String viewName) {
        onView(withId(R.id.menu_dropdown)).perform(click());
        onView(withText(viewName)).perform(click());
    }

    @Test
    public void testAddGoalsToTomorrowAndPending() throws InterruptedException {
        // Verify default Today view shows date
        onView(withText("Today")).check(matches(isDisplayed()));

        // Select Tomorrow view
        selectView("Tomorrow");

        // Verify Tomorrow title is displayed (with a short delay)
        Thread.sleep(500); // Adjust delay if needed
        onView(withText("Tomorrow")).check(matches(isDisplayed()));

        // Verify empty list is shown
        // ... (Add assertion for empty list)

        // Create a goal for Tomorrow
        createGoal("Go to gym");

        // Verify goal added to Tomorrow view
        onData(anything())
                .inAdapterView(withId(R.id.goal_list))
                .atPosition(0)
                .onChildView(withId(R.id.goal_content_text))
                .check(matches(withText("Go to gym")));

        // Select Pending view
        selectView("Pending");

        // Verify Pending title is displayed
        onView(withText("Pending")).check(matches(isDisplayed()));

        // Create a goal for Pending
        createGoal("Research trips");

        // Verify goal added to Pending view
        onData(anything())
                .inAdapterView(withId(R.id.goal_list))
                .atPosition(0)
                .onChildView(withId(R.id.goal_content_text))
                .check(matches(withText("Research trips")));
    }
}