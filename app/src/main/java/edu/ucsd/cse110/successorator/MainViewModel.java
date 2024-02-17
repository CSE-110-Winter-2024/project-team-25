package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class MainViewModel extends ViewModel {
    private GoalRepository goalRepository;
    private MutableSubject<List<Goal>> unCompleteGoalList;
    private MutableSubject<List<Goal>> completeGoalList;
    private MutableSubject<List<Goal>> goalList;
    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getGoalRepository());
                    });
    public MainViewModel(GoalRepository goalRepo){
        this.goalRepository = goalRepo;
        this.unCompleteGoalList = new SimpleSubject<>();
        this.completeGoalList = new SimpleSubject<>();
        this.goalList = new SimpleSubject<>();
        this.goalRepository.getAllUncompleteGoals().observe(goals -> {
            if(goals==null){
                return;
            }
            List<Goal> newUncompleteList = new ArrayList<>(goals);
            unCompleteGoalList.setValue(newUncompleteList);
            goalList.setValue(goalRepo.getAllGoal());
        });
        this.goalRepository.getAllCompleteGoals().observe(goals -> {
            if(goals==null){
                return;
            }
            List<Goal> newCompleteList = new ArrayList<>(goals);
            completeGoalList.setValue(newCompleteList);
            goalList.setValue(goalRepo.getAllGoal());
        });

    }
    public void rollOver(){
        goalRepository.rollOver();
    }

    public MutableSubject<List<Goal>> getGoalList() {
        return goalList;
    }

    public void changeGoalStatus(int id, boolean isComplete){
        goalRepository.changeIsCompleteStatus(id, isComplete);
    }

    public MutableSubject<List<Goal>> getUnCompleteGoalList() {
        return unCompleteGoalList;
    }

    public int addGoal(String content){
        return goalRepository.addGoal(content);
    }
}
