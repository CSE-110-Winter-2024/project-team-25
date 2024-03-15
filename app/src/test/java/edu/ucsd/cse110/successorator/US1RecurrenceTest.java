package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.RecurrenceFactory;

public class US1RecurrenceTest
{

    private Recurrence recurrence;
    private Date startDate;

    @Before
    public void setUp()
    {
        // Set up a test Recurrence object with a first occurrence on January 1, 2024,
        // and a daily recurrence pattern.
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        startDate = new Date(calendar);

        List<Period> pattern = new ArrayList<>();
        pattern.add(Period.ofDays(1));
        recurrence = new Recurrence(startDate, pattern);
    }

    @Test
    public void testRecurrenceInitialization()
    {
        assertNotNull(recurrence);
        assertEquals(startDate, recurrence.getFirstOccurrence());
    }
    @Test
    public void TestIsFutureRecurrence(){
        Date startDate = new Date(new GregorianCalendar(2024, 0, 15));
        Recurrence rec_Yearly = RecurrenceFactory.createYearlyRecurrence(startDate);
        Recurrence rec_Monthly = RecurrenceFactory.createMonthlyRecurrence(startDate);
        Recurrence rec_Weekly = RecurrenceFactory.createWeeklyRecurrence(startDate);
        Recurrence rec_Daily = RecurrenceFactory.createDailyRecurrence(startDate);

        //test case one: date < startDate
        Date testDate = new Date(new GregorianCalendar(2022, 9, 11));
        assertFalse(rec_Daily.isFutureRecurrence(testDate));
        //test case: daily recurrence
        testDate = new Date(new GregorianCalendar());
        assertTrue(rec_Daily.isFutureRecurrence(testDate));
        //test case: weekly recurrence
        testDate = new Date(new GregorianCalendar(2024, 0, 15));
        assertTrue(rec_Weekly.isFutureRecurrence(testDate));
        testDate = new Date(new GregorianCalendar(2024, 0, 22));
        assertTrue(rec_Weekly.isFutureRecurrence(testDate));
        testDate = new Date(new GregorianCalendar(2024, 0, 21));
        assertFalse(rec_Weekly.isFutureRecurrence(testDate));

        //test case: Monthly recurrence (2.12/ 3.11) false (2.5/3.18)
        testDate = new Date(new GregorianCalendar(2024, 1, 12));
        assertEquals(Calendar.FEBRUARY, testDate.getCalendar().get(Calendar.MONTH));
        assertTrue(rec_Monthly.isFutureRecurrence(testDate));
        testDate = new Date(new GregorianCalendar(2024, 2, 11));
        assertTrue(rec_Monthly.isFutureRecurrence(testDate));
        testDate = new Date(new GregorianCalendar(2024, 1, 5));
        assertFalse(rec_Monthly.isFutureRecurrence(testDate));
        testDate = new Date(new GregorianCalendar(2024, 2, 18));
        assertFalse(rec_Monthly.isFutureRecurrence(testDate));

        //test case: Yearly recurrence (2025.1.15 2024.1.15 2024.12.8)
        testDate = new Date(new GregorianCalendar(2024, 0, 15));
        assertTrue(rec_Yearly.isFutureRecurrence(testDate));
        testDate = new Date(new GregorianCalendar(2024, 11, 8));
        assertFalse(rec_Yearly.isFutureRecurrence(testDate));
        testDate = new Date(new GregorianCalendar(2025, 0, 15));
        assertTrue(rec_Yearly.isFutureRecurrence(testDate));

        startDate = new Date(new GregorianCalendar(2024, 2, 15));
        rec_Daily = RecurrenceFactory.createDailyRecurrence(startDate);
        testDate = new Date(new GregorianCalendar(2024, 2, 15));
        assertTrue(rec_Daily.isFutureRecurrence(testDate));
    }
}

