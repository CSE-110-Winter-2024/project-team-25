package edu.ucsd.cse110.successorator.lib.util;

import edu.ucsd.cse110.successorator.lib.domain.Date;

public interface TimeKeeper {
    Subject<Date> getDateAsSubject();
    Date getDate();
}
