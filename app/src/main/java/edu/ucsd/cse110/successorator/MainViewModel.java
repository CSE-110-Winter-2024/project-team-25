package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.stream.Collectors;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.DateUpdater;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    private final GoalRepository goalRepository;
    private final DateUpdater dateUpdater;

    // UI state
    private final MutableSubject<String> dateString;
    private final MutableSubject<List<Goal>> orderedGoals;
    private final MutableLiveData<Boolean> isGoalListEmpty;

    private final MutableSubject<Boolean> isDateChange;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getGoalRepository());
                    });

    public MainViewModel(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
        this.isDateChange = new SimpleSubject<>();

        // Create the observable subjects.
        this.orderedGoals = new SimpleSubject<>();
        this.dateString = new SimpleSubject<>();
        this.dateUpdater = new DateUpdater(MainActivity.DELAY_HOUR);
        this.isGoalListEmpty = new MutableLiveData<>();

        // When the goals change, update the ordering.
        goalRepository.getAllGoals().observe(goals -> {
            if (goals == null) return;
            var ordered = goals.stream()
                    .sorted()
                    .collect(Collectors.toList());
            this.orderedGoals.setValue(ordered);
            isGoalListEmpty.setValue(goals.isEmpty());
        });

        dateUpdater.getDateString().observe(dateString::setValue);
    }



    public Subject<List<Goal>> getOrderedGoals() {
        return orderedGoals;
    }


    public void rollOver(){
        goalRepository.rollOver();
    }

    public void toggleGoalStatus(int id){
        goalRepository.toggleIsCompleteStatus(id);
    }

    public void deleteGoal(int id){
        goalRepository.deleteGoal(id);
    }

    //return value of int for easy testing
    public int addGoal(String content){
        return goalRepository.addGoal(content);
    }

    public int addGoal(Goal goal) {return goalRepository.addGoal(goal);}
    public LiveData<Boolean> getIsGoalListEmpty() {
        return isGoalListEmpty;
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }

    public void updateDateWithRollOver(SharedPreferences sharedPref, int hourChange, boolean doSync){
        if(doSync){
            dateUpdater.syncDate();
        }
        dateUpdater.dateUpdate(hourChange);
        String curTime = dateString.getValue();
        String ResumeTime = sharedPref.getString(MainActivity.lastResumeTime, "");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(MainActivity.lastResumeTime, dateString.getValue());
        editor.apply();
        if(!ResumeTime.equals(curTime)){
            rollOver();
        }
    }
}
