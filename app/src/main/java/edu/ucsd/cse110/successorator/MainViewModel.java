package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalWithDate;
import edu.ucsd.cse110.successorator.lib.domain.Type;
import edu.ucsd.cse110.successorator.lib.util.DateUpdater;
import edu.ucsd.cse110.successorator.lib.util.GoalDateComparator;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    private final GoalRepository goalRepository;
    private final DateUpdater dateUpdater;
    private final MutableSubject<String> dateString;
    private final MutableSubject<Date> dateSubject; //Date
    private Date tomorrow;
    private Date today;
    private Date targetDate;
    private final MutableSubject<List<Goal>> orderedGoals;
    private final MutableLiveData<Boolean> isGoalListEmpty;
    private final MutableSubject<Type> typeSubject;
    private final MutableSubject<Integer> adapterIndex;
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
        this.typeSubject = new SimpleSubject<>();
        this.dateString = new SimpleSubject<>();
        this.dateSubject = new SimpleSubject<>();
        this.dateUpdater = new DateUpdater(MainActivity.DELAY_HOUR);
        this.targetDate = dateUpdater.getDate();
        this.dateSubject.setValue(targetDate.clone());
        //refractor this to dateUpdater class!
        this.tomorrow = dateUpdater.getDate().clone();
        this.tomorrow.hourAdvance(24);
        this.today = dateUpdater.getDate();
        this.isGoalListEmpty = new MutableLiveData<>();
        this.adapterIndex = new SimpleSubject<>();
        adapterIndex.setValue(0);
        typeSubject.setValue(Type.TODAY);
        typeSubject.observe(
                type -> {
                        orderedGoals.setValue(GoalDateComparator.createGoalListWithDate(
                        targetDate, goalRepository.getAllGoals(), type));
                        isGoalListEmpty.setValue(orderedGoals.getValue().isEmpty());
                }
        );
        goalRepository.getAllGoalsAsSubject().observe(goals -> {
            if (goals == null) return;
            orderedGoals.setValue(GoalDateComparator.createGoalListWithDate(
                    targetDate, goals, typeSubject.getValue()));
            isGoalListEmpty.setValue(orderedGoals.getValue().isEmpty());
        });
        dateUpdater.getDateString().observe(dateString::setValue);
        dateUpdater.getDateAsSubject().observe(date -> dateSubject.setValue(date.clone()));
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
        if(goalRepository.find(id) instanceof RecurringGoal){
            goalRepository.deleteRecurringGoalWithDateByRecurrenceID(id);
        }
        goalRepository.deleteGoal(id);
    }
    public int addGoal(Goal goal) {
        int id = goalRepository.addGoal(goal);
        if(goal instanceof RecurringGoal) {
            addRecurringGoalWithDate(goalRepository.find(id));
        }
        return id;
    }

    public void addRecurringGoalWithDate(Goal goal){
        if(goal instanceof RecurringGoal){
            if(((RecurringGoal)goal).getRecurrence().isFutureRecurrence(today)){
                RecurringGoalWithDate goal_today = ((RecurringGoal) goal).createRecurringGoalWithDate(today);
                addGoal(goal_today);
            }
            if(((RecurringGoal)goal).getRecurrence().isFutureRecurrence(tomorrow)){
                RecurringGoalWithDate goal_tomorrow = ((RecurringGoal) goal).createRecurringGoalWithDate(tomorrow);
                addGoal(goal_tomorrow);
            }
        }
    }

    public LiveData<Boolean> getIsGoalListEmpty() {
        return isGoalListEmpty;
    }

    public MutableSubject<String> getDateString(){
        return dateString;
    }

    public MutableSubject<Date> getDateSubject(){return dateSubject;}

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
            //targetDate
            today = dateUpdater.getDate().clone();
            tomorrow = dateUpdater.getDate().clone();
            tomorrow.hourAdvance(24);
            addGoalwithRecurrence(tomorrow);
            deleteGoalWithRecurrence(today);
            rollOver();
            listSelector(adapterIndex.getValue());
        }
    }

    private synchronized void addGoalwithRecurrence(Date date){
        goalRepository.getAllGoals()
                .stream()
                .filter(goal -> goal instanceof RecurringGoal&&
                        ((RecurringGoal) goal).getRecurrence().isFutureRecurrence(date))
                .forEach(
                        goal -> {
                            RecurringGoalWithDate newGoalWithDate = ((RecurringGoal) goal).createRecurringGoalWithDate(date);
                            goalRepository.addGoal(newGoalWithDate);
                        }
                );
    }

    private synchronized void deleteGoalWithRecurrence(Date date){
        goalRepository.getAllGoals()
                .stream()
                .filter(goal -> goal instanceof RecurringGoalWithDate &&
                        ((RecurringGoalWithDate) goal).getDate().compareTo(date)<=0)
                .forEach(
                        goal -> {
                            if (GoalDateComparator.hasRedundancy(date, (RecurringGoalWithDate) goal,
                                    goalRepository.getAllGoals()) &&!goal.isComplete()) {
                                goalRepository.deleteGoal(goal.getId());
                            }
                        }
                );
    }

    public void listSelector(int item_id){
        switch(item_id) {
            case 0:
                adapterIndex.setValue(0);
                this.targetDate = today;
                this.dateSubject.setValue(targetDate.clone());
                typeSubject.setValue(Type.TODAY);
                break;
            case 1:
                adapterIndex.setValue(1);
                this.targetDate = tomorrow;
                this.dateSubject.setValue(targetDate.clone());
                typeSubject.setValue(Type.TOMORROW);
                break;
            case 2:
                adapterIndex.setValue(2);
                typeSubject.setValue(Type.RECURRENCE);
                break;
        }
    }
    public Subject<Integer> getAdapterIndexAsSubject(){
        return adapterIndex;
    }
    public Date getTargetDate(){
        return targetDate.clone();
    }
}