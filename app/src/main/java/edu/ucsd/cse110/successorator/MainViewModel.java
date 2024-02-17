package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    private final GoalRepository goalRepository;

    // UI state
    private final MutableSubject<List<Goal>> orderedGoals;
    private final MutableSubject<Goal> topGoal;
    private final MutableSubject<String> displayedText;

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

        // Create the observable subjects.
        this.orderedGoals = new SimpleSubject<>();
        this.topGoal = new SimpleSubject<>();
        this.displayedText = new SimpleSubject<>();


        goalRepository.getAllUncompleteGoals().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedCards = goals.stream()
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            orderedGoals.setValue(newOrderedCards);
        });

        // When the ordering changes, update the top card.
        orderedGoals.observe(goals -> {
            if (goals == null || goals.size() == 0) return;
            var goal = goals.get(0);
            this.topGoal.setValue(goal);
        });

        // When the top card changes, update the displayed text and display the front side.
        topGoal.observe(goal -> {
            if (goal == null) return;
            displayedText.setValue(goal.getContent());
        });

    }

    public Subject<String> getDisplayedText() {
        return displayedText;
    }

    public Subject<List<Goal>> getOrderedGoals() {
        return orderedGoals;
    }

    public void rollOver(){
        goalRepository.rollOver();
    }

    public void changeGoalStatus(int id, boolean isComplete){
        goalRepository.changeIsCompleteStatus(id, isComplete);
    }

    public int addGoal(String content){
        return goalRepository.addGoal(content);
    }
}
