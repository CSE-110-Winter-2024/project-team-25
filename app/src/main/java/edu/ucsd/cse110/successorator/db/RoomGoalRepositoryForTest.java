package edu.ucsd.cse110.successorator.db;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class RoomGoalRepositoryForTest extends RoomGoalRepository {
    public RoomGoalRepositoryForTest(GoalDao goalDao){
        super(goalDao);
    }

    public Goal find(int id) {
        return goalDao.find(id).toGoal();
    }

    public GoalDao getGoalDao() {
        return goalDao;
    }
}
