package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.goallist.GoalListFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ucsd.cse110.successorator.ui.goallist.GoalListFragment;
import edu.ucsd.cse110.successorator.ui.goallist.dialog.CreateGoalDialogFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
    private MainViewModel model;
    private GoalListFragment goalList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.goalList = GoalListFragment.newInstance();
      
        //To be modified
        DateFormat dateFormat = new SimpleDateFormat("EEEE, MM/dd");
        Date date = new Date();
        setTitle(dateFormat.format(date));
      
        //^^^
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, this.goalList)
                .commit();
      
        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        this.model = new MainViewModel(((SuccessoratorApplication) getApplication()).getGoalRepository());

        setContentView(view.getRoot());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editgoal, menu);
        menu.findItem(R.id.edit_bar_menu_add_goal).setOnMenuItemClickListener(item -> {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(goalList.getParentFragmentManager(), "CreateGoalDialogFragment");
            return true;
        });
        return true;
    }
}
