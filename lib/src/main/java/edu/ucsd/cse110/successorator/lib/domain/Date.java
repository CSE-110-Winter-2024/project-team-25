package edu.ucsd.cse110.successorator.lib.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class Date{
    private LocalDateTime localDateTime;
    private String format = "EEEE, MM/dd";

    public Date(LocalDateTime localDateTime){
        this.localDateTime = localDateTime;
    }

    public void dayIncrement(){
        this.localDateTime = localDateTime.plus(1, ChronoUnit.DAYS);
    }

    public LocalDateTime getLocalDateTime() {
        this.localDateTime = LocalDateTime.now();
        return localDateTime;
    }

    public String getTime(){
        this.localDateTime = LocalDateTime.now();
        return String.valueOf(localDateTime);
    }
}
