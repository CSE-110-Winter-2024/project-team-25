package edu.ucsd.cse110.successorator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;



import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;


import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.util.DateFormatter;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.ui.goallist.GoalListFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.ucsd.cse110.successorator.ui.goallist.dialog.CreateGoalDialogFragment;

public class MainActivity extends AppCompatActivity {
    public static final int DELAY_HOUR = -2;
    private static final int HOUR_IN_DAY =24;
    public static final String lastResumeTime = "LASTRESUMETIME";
    private ActivityMainBinding view;
    private MainViewModel activityModel;
    private GoalListFragment goalList;


    private DateFormatter dateFormatter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar materialToolbar;
    private FrameLayout frameLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dateFormatter = new DateFormatter();
        this.goalList = GoalListFragment.newInstance();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(this, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
        this.activityModel.getDateSubject().observe(date -> {
            setTitle(dateFormatter.getFormatDate(date.getCalendar()));
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, this.goalList)
                .commit();
        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        //starts here
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);


        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        activityModel.updateDateWithRollOver(sharedPref, DELAY_HOUR, true);

        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.context_home){
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_focus);

                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    activityModel.activate_focus_mode(edu.ucsd.cse110.successorator.lib.domain.Context.HOME);
                    activityModel.listSelector(activityModel.getadapterIndex());
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (item.getItemId()==R.id.context_work){
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_focus);
                    Toast.makeText(MainActivity.this, "work", Toast.LENGTH_SHORT).show();
                    activityModel.activate_focus_mode(edu.ucsd.cse110.successorator.lib.domain.Context.WORK);
                    activityModel.listSelector(activityModel.getadapterIndex());
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (item.getItemId()==R.id.context_school){
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_focus);
                    Toast.makeText(MainActivity.this, "school", Toast.LENGTH_SHORT).show();
                    activityModel.activate_focus_mode(edu.ucsd.cse110.successorator.lib.domain.Context.SCHOOL);
                    activityModel.listSelector(activityModel.getadapterIndex());
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (item.getItemId()==R.id.context_errands){
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_focus);
                    Toast.makeText(MainActivity.this, "errand", Toast.LENGTH_SHORT).show();
                    activityModel.activate_focus_mode(edu.ucsd.cse110.successorator.lib.domain.Context.ERRANDS);
                    activityModel.listSelector(activityModel.getadapterIndex());
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else if (item.getItemId()==R.id.context_cancel){
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
                    Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    activityModel.deactivate_focus_mode();
                    activityModel.listSelector(activityModel.getadapterIndex());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    drawerToggle.syncState();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
//                drawerToggle.syncState();
                return true;
            }
        });

    }

    @Override
    public void onResume(){
        Log.d("onResume", "onResumeStatus: True");
        super.onResume();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        //activityModel.updateDateWithRollOver(sharedPref, DELAY_HOUR, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editgoal, menu);
        //getMenuInflater().inflate(R.menu.header_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.edit_bar_menu_add_goal) {
            // Handle "Add Goal" action
            CreateGoalDialogFragment dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "CreateGoalDialogFragment");
            return true;
        } else if (itemId == R.id.edit_bar_menu_edit_date) {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            activityModel.updateDateWithRollOver(sharedPref, HOUR_IN_DAY, false);
            return true;
        }
        else if (itemId == R.id.today_option) {
            activityModel.listSelector(0);
            return true;
        } else if (itemId == R.id.tomorrow_option) {
            activityModel.listSelector(1);
            return true;
        }
        else if (itemId == R.id.pending_option) {
            setTitle("Pending");
            activityModel.listSelector(3);
            return true;
        }
        else if (itemId == R.id.recurring_option) {
            setTitle("Recurring");
            activityModel.listSelector(2);
            return true;
        }
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

}
