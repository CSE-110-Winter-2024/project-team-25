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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
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
    private SimpleDateFormat dateFormatter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar materialToolbar;
    private FrameLayout frameLayout;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dateFormatter = new SimpleDateFormat("EEEE, MM/dd");
        this.goalList = GoalListFragment.newInstance();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(this, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
        this.activityModel.getDateSubject().observe(date -> {
            Log.d("check line", "41");
            setTitle(dateFormatter.format(date.getCalendar().getTime()));
        });

        //^^^
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, this.goalList)
                .commit();

        this.view = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(view.getRoot());
        drawerLayout = findViewById(R.id.drawerlayout);
//        materialToolbar = findViewById(R.id.materialToolbar);
//        frameLayout = findViewById(R.id.fragment_container);
        navigationView = findViewById(R.id.navigationView);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);

//        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
//            @Override
//            public boolean onMenuItemClick(MenuItem item){
//                if(item.getItemId()==R.id.edit_bar_menu_add_goal){
//                    Toast.makeText(MainActivity.this, "add goal", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });
        drawerToggle.syncState();;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.context_home){
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (item.getItemId()==R.id.context_work){
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else if (item.getItemId()==R.id.context_school){
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else if (item.getItemId()==R.id.context_errands){
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public void onResume(){
        Log.d("onResume", "onResumeStatus: True");
        super.onResume();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        activityModel.updateDateWithRollOver(sharedPref, DELAY_HOUR, true);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        var itemId = item.getItemId();
//
//        if (itemId == R.id.edit_bar_menu_add_goal) {
//            var dialogFragment = CreateGoalDialogFragment.newInstance();
//            dialogFragment.show(goalList.getParentFragmentManager(), "CreateGoalDialogFragment");
//        }
//        else if(itemId == R.id.edit_bar_menu_edit_date){
//            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//            activityModel.updateDateWithRollOver(sharedPref, HOUR_IN_DAY, false);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editgoal, menu);
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
            // Handle "Edit Date" action
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            activityModel.updateDateWithRollOver(sharedPref, HOUR_IN_DAY, false);
            return true;
        }
        else if (itemId == R.id.today_option) {
            this.activityModel.getDateString().observe(dateString -> {
                setTitle(dateString);
            });

            // Call switch to Today's Goalist
            return true;
        } else if (itemId == R.id.tomorrow_option) {
            Calendar c = Calendar.getInstance();
            if(c.get(Calendar.HOUR) >= 2) {
                c.add(Calendar.DATE, 1);
            }
            String tomorrowString = new SimpleDateFormat("EEEE, MM/dd").format(c.getTime());
            this.activityModel.getDateString().observe(dateString -> {
                setTitle(tomorrowString);
            });
            // Call switch to Tomorrow's Goalist
            return true;
        }
        else if (itemId == R.id.pending_option) {
            this.activityModel.getDateString().observe(dateString -> {
                setTitle("Pending");
            });
            // Call switch to Pending's Goalist
            return true;
        }
        else if (itemId == R.id.recurring_option) {
            this.activityModel.getDateString().observe(dateString -> {
                setTitle("Recurring");
            });
            // Call switch to Recurring's Goalist


            return true;
        }

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
