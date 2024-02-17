package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import edu.ucsd.cse110.successorator.db.GoalDao;
import edu.ucsd.cse110.successorator.db.RoomGoalRepositoryForTest;
import edu.ucsd.cse110.successorator.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

@RunWith(AndroidJUnit4.class)
public class testGoalRepository {
    private RoomGoalRepositoryForTest roomRepo;
    private SuccessoratorDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, SuccessoratorDatabase.class).build();
        roomRepo = new RoomGoalRepositoryForTest(db.goalDao());
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }
    @Test
    public void testMxSortOrder() throws Exception {
        GoalDao dao = roomRepo.getGoalDao();
        for(int i = 0; i<10; i++){
            Integer sortNum = dao.getMaxSortOrder();
            roomRepo.addGoal("MIT"+i);
            assertEquals(sortNum, (Integer)i);
        }
    }
    @Test
    public void testWriteGoal() throws Exception {
        GoalDao dao = roomRepo.getGoalDao();
        for(int i = 10; i<20; i++){
            Integer sortNum = dao.getMaxSortOrder();
            int id = roomRepo.addGoal("MIT"+i);
            Goal result = roomRepo.find(id);
            Goal expected = new Goal(id, "MIT"+i, false, sortNum+1);
            assertEquals(result, expected);
        }
    }
    @Test
    public void changeCompleteStatus(){
        for(int i = 10; i<20; i++){
            int id = roomRepo.addGoal("MIT"+i);
            roomRepo.changeIsCompleteStatus(id, true);
            assertTrue(roomRepo.find(id).isComplete());
            roomRepo.changeIsCompleteStatus(id, false);
            assertFalse(roomRepo.find(id).isComplete());
        }
    }
    @Test
    public void testRollOver(){
        for(int i = 10; i<20; i++){
            int id = roomRepo.addGoal("MIT"+i);
            roomRepo.changeIsCompleteStatus(id, true);
        }
        for(int i = 0; i<10; i++){
            int id = roomRepo.addGoal("MIT"+i);
        }
        roomRepo.rollOver();
        List<Goal> unremovedGoal = roomRepo.getAllGoals().getValue();
        assertEquals(10, unremovedGoal.size());
        int label = 0;
        for(var goal : unremovedGoal){
            assertFalse(goal.isComplete());
            assertEquals(goal.getContent(), "MIT"+label);
            assertEquals((Integer)goal.getSortOrder(), (Integer)(label+11));
            label++;
        }
    }
//
//    @Test
//    public void getUncompleteGoal(){
//        int id = roomRepo.addGoal("MIT");
//        roomRepo.getAllUncompleteGoals().observe(goal -> {});
//        assertEquals(1, roomRepo.getAllUncompleteGoals().getValue().size());
//        roomRepo.changeIsCompleteStatus(id, true);
//    }
//    @Test
//    public void getCompleteGoal(){
//
//    }
}
