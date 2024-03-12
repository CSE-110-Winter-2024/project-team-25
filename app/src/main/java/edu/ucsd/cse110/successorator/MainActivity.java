package edu.ucsd.cse110.successorator;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.navigation.NavigationView;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.goallist.GoalListFragment;

import edu.ucsd.cse110.successorator.ui.goallist.dialog.CreateGoalDialogFragment;

public class MainActivity extends AppCompatActivity {
    public static final int DELAY_HOUR = -2;
    private static final int HOUR_IN_DAY =24;
    public static final String lastResumeTime = "LASTRESUMETIME";
    private ActivityMainBinding view;
    private MainViewModel activityModel;
    private GoalListFragment goalList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.goalList = GoalListFragment.newInstance();

        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(this, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
        this.activityModel.getDateString().observe(dateString -> {
            setTitle(dateString);
        });
      
        //^^^
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, this.goalList)
                .commit();
      
        this.view = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(view.getRoot());
        drawerLayout = findViewById(R.id.fragment_container);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.home:
//                    {
//                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                    }
//                }
                return false;
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.edit_bar_menu_add_goal) {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(goalList.getParentFragmentManager(), "CreateGoalDialogFragment");
        }
        else if(itemId == R.id.edit_bar_menu_edit_date){
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            activityModel.updateDateWithRollOver(sharedPref, HOUR_IN_DAY, false);
        }
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editgoal, menu);
        return true;
    }



}
