package edu.ucsd.cse110.successorator.lib.domain;

import java.time.Period;

public interface IRecurrence {
    public Date getNextDate(Date date);
}
