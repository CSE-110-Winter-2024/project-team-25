package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.goalRepository;

public class SuccessoratorApplication extends Application {
    goalRepository goalRepo;
    public void onCreate(){
        super.onCreate();
        var database = Room.databaseBuilder(
                getApplicationContext(),
                SuccessoratorDatabase.class,
                "goals-database"
        )
                .allowMainThreadQueries()
                .build();
        this.goalRepo = new RoomGoalRepository(database.goalDao());
    }
    public goalRepository getGoalRepository(){
        return goalRepo;
    }
}
