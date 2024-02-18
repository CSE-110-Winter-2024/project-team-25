package edu.ucsd.cse110.successorator;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.goallist.GoalListFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ucsd.cse110.successorator.ui.goallist.GoalListFragment;
import edu.ucsd.cse110.successorator.ui.goallist.dialog.CreateGoalDialogFragment;

public class MainActivity extends AppCompatActivity {
    private final String lastResumeTime = "LASTRESUMETIME";
    private ActivityMainBinding view;
    private MainViewModel activityModel;
    private GoalListFragment goalList;
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
//        this.activityModel.getIsDateChange().observe(hasChange ->{
//           if(hasChange){
//               this.activityModel.rollOver();
//           }
//        });

      
        //^^^
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, this.goalList)
                .commit();
      
        this.view = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(view.getRoot());
    }

    @Override
    public void onResume(){
        Log.d("onResume", "onResumeStatus: True");
        super.onResume();
        activityModel.checkDate();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String ResumeTime = sharedPref.getString(lastResumeTime, "");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(lastResumeTime, activityModel.getDateString().getValue());
        editor.apply();
        if(!ResumeTime.equals(activityModel.getDateString().getValue())){
            activityModel.checkDate();
            activityModel.rollOver();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.edit_bar_menu_add_goal) {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(goalList.getParentFragmentManager(), "CreateGoalDialogFragment");
        }
        else if(itemId == R.id.edit_bar_menu_edit_date){
            activityModel.dayIncrement();
            activityModel.rollOver();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editgoal, menu);
        return true;
    }
}
