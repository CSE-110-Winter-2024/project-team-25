package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.db.RoomGoalRepositoryForTest;
import edu.ucsd.cse110.successorator.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.GoalRepository;

public class SuccessoratorApplication extends Application {
    GoalRepository goalRepo;
    public void onCreate(){
        super.onCreate();
        var database = Room.databaseBuilder(
                getApplicationContext(),
                SuccessoratorDatabase.class,
                "goals-database"
        )
                .allowMainThreadQueries()
                .build();
        this.goalRepo = new RoomGoalRepositoryForTest(database.goalDao());
    }
    public GoalRepository getGoalRepository(){
        return goalRepo;
    }
}
