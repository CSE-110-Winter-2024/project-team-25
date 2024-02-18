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

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
    private MainViewModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
      
        //To be modified
        DateFormat dateFormat = new SimpleDateFormat("EEEE, MM/dd");
        Date date = new Date();
        setTitle(dateFormat.format(date));
      
        //^^^
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, GoalListFragment.newInstance())
                .commit();
      
        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        this.model = new MainViewModel(((SuccessoratorApplication) getApplication()).getGoalRepository());

        setContentView(view.getRoot());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editgoal, menu);
        return true;
    }
}
