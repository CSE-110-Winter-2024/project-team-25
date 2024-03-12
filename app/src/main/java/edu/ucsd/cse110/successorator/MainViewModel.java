package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Stream;

import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.DatedGoal;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.util.DateUpdater;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    private final GoalRepository goalRepository;
    private final DateUpdater dateUpdater;

    // UI state
    private final MutableSubject<String> dateString;
    private final MutableSubject<Date> dateSubject;
    private final Date tomorrow;
    private final MutableSubject<List<Goal>> orderedGoals;
    private final MutableLiveData<Boolean> isGoalListEmpty;
    private List<List<Goal>> ListDict;
    private List<Goal> today_Goals;
    private List<Goal> tomorrow_Goals;
    private List<Goal> pending_Goals;
    private List<Goal> recurring_Goals;

    private final int[] listIndex = {0};

    private final MutableSubject<Integer> adapterIndex;
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
        this.dateSubject = dateUpdater.getDateAsSubject();
        this.tomorrow = dateUpdater.getDate().clone();
        this.tomorrow.hourAdvance(24);
        this.isGoalListEmpty = new MutableLiveData<>();
        this.today_Goals = new ArrayList<>();
        this.tomorrow_Goals = new ArrayList<>();
        this.recurring_Goals = new ArrayList<>();
        this.pending_Goals = new ArrayList<>();
        this.adapterIndex = new SimpleSubject<>();
        this.ListDict = Arrays.asList(today_Goals, recurring_Goals);
        adapterIndex.setValue(listIndex[0]);
        // When the goals change, update the ordering.
        goalRepository.getAllGoalsAsSubject().observe(goals -> {
            if (goals == null) return;
            today_Goals = Stream.concat(
                    goals.stream()
                        .filter(goal -> goal instanceof DatedGoal &&
                                dateUpdater.getDate().compareTo(((DatedGoal) goal).getDate())>=0
                        ),
                    goals.stream()
                        .filter(goal -> goal instanceof RecurringGoal &&
                                !((RecurringGoal) goal).getIsFinished() &&
                                dateUpdater.getDate().compareTo(((RecurringGoal)goal).getRecurrence().getFirstOccurrence())>=0)
                    )
                        .sorted()
                        .collect(Collectors.toList());
            tomorrow_Goals = Stream.concat(
                            goals.stream()
                                    .filter(goal -> goal instanceof DatedGoal &&
                                            tomorrow.equals(((DatedGoal) goal).getDate())
                                    ),
                            goals.stream()
                                    .filter(goal -> goal instanceof RecurringGoal &&
                                            !((RecurringGoal) goal).getIsFinished() &&
                                            tomorrow.compareTo(((RecurringGoal)goal).getRecurrence().getFirstOccurrence())>=0
                                            )
                    )
                    .sorted()
                    .collect(Collectors.toList());
            ListDict.set(0, today_Goals);
            recurring_Goals = goals.stream()
                    .filter(goal -> goal instanceof RecurringGoal)
                    .collect(Collectors.toList());
            ListDict.set(1, recurring_Goals);
            orderedGoals.setValue(ListDict.get(listIndex[0]));
            isGoalListEmpty.setValue(orderedGoals.getValue().isEmpty());
        });
        dateUpdater.getDateString().observe(dateString::setValue);
        dateUpdater.getDateAsSubject().observe(dateSubject::setValue);
    }

    public synchronized void updateRecurrence(){
        //List<Goal> updateGoal =
        goalRepository.getAllGoals()
                .stream()
                .filter(goal -> goal instanceof RecurringGoal &&
                        //dateUpdater.getDate().equals(((RecurringGoal)goal).getRecurrence().getNextOccurrence()))
                        tomorrow.equals(((RecurringGoal)goal).getRecurrence().getNextOccurrence()))
                .forEach(goal -> {
                    ((RecurringGoal) goal).getRecurrence().applyRecurrence();
                    goalRepository.updateIsFinish(goal.getId(), ((RecurringGoal) goal).getRecurrence(), false);
                    if(goal.isComplete())
                        goalRepository.toggleIsCompleteStatus(goal.getId());
                }
        );
                //.collect(Collectors.toList());

    }


    public Subject<List<Goal>> getOrderedGoals() {
        return orderedGoals;
    }


    public synchronized void rollOver(){
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

    public int addGoal(Goal goal) {
        if(goal instanceof RecurringGoal && ((RecurringGoal)goal).getRecurrence().applyRecurrence().equals(dateUpdater.getDate()))
            ((RecurringGoal)goal).getRecurrence().applyRecurrence();
//            int id = goalRepository.addGoal(goal);
//            goalRepository.updateIsFinish(goal.getId(), ((RecurringGoal) goal).getRecurrence(), false);
//            return id;
//        }else{
        return goalRepository.addGoal(goal);
//        }
    }
    public LiveData<Boolean> getIsGoalListEmpty() {
        return isGoalListEmpty;
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }

    public MutableSubject<Date> getDateSubject(){return dateSubject;}

    public void setToday_Goals(){
        orderedGoals.setValue(List.copyOf(today_Goals));
    }
    public void setRecurring_Goals(){
        orderedGoals.setValue(List.copyOf(recurring_Goals));
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
            updateRecurrence();
            rollOver();
        }
    }

    public void listSelector(int item_id){
        switch(item_id) {
            case 0:
                listIndex[0]=0;
                adapterIndex.setValue(0);
                setToday_Goals();
                break;
            case 1:
                listIndex[0]=1;
                adapterIndex.setValue(1);
                setRecurring_Goals();
                break;
            default:
                listIndex[0]=0;
        }
    }

    public Subject<Integer> getAdapterIndexAsSubject(){
        return adapterIndex;
    }
}
