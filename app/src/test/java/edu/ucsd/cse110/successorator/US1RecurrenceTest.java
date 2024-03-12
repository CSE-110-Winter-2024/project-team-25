package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    assertEquals(startDate, recurrence.getNextOccurrence());
  }

  @Test
  public void testApplyRecurrence()
  {
    // First occurrence should be January 1, 2024.
    Date firstOccurrence = recurrence.applyRecurrence();
    assertEquals(2024, firstOccurrence.getCalendar().get(Calendar.YEAR));
    assertEquals(Calendar.JANUARY,
                 firstOccurrence.getCalendar().get(Calendar.MONTH));
    assertEquals(1, firstOccurrence.getCalendar().get(Calendar.DAY_OF_MONTH));

    // Next occurrence should be January 2, 2024.
    Date secondOccurrence = recurrence.applyRecurrence();
    assertEquals(2024, secondOccurrence.getCalendar().get(Calendar.YEAR));
    assertEquals(Calendar.JANUARY,
                 secondOccurrence.getCalendar().get(Calendar.MONTH));
    assertEquals(2, secondOccurrence.getCalendar().get(Calendar.DAY_OF_MONTH));

    // Next occurrence should be January 3, 2024.
    Date thirdOccurrence = recurrence.applyRecurrence();
    assertEquals(2024, thirdOccurrence.getCalendar().get(Calendar.YEAR));
    assertEquals(Calendar.JANUARY,
                 thirdOccurrence.getCalendar().get(Calendar.MONTH));
    assertEquals(3, thirdOccurrence.getCalendar().get(Calendar.DAY_OF_MONTH));

    // After applying the recurrence many times, check if it cycles properly.
    for (int i = 0; i < 363; i++)
    {
      recurrence.applyRecurrence();
    }
    // Next occurrence should be January 1, 2025, as the recurrence should cycle yearly.
    Date cycleOccurrence = recurrence.applyRecurrence();
    assertEquals(2025, cycleOccurrence.getCalendar().get(Calendar.YEAR));
    assertEquals(Calendar.JANUARY,
                 cycleOccurrence.getCalendar().get(Calendar.MONTH));
    assertEquals(1, cycleOccurrence.getCalendar().get(Calendar.DAY_OF_MONTH));
  }
  @Test
  public void TestMonthlyRecurrence() {
    Date startDate = new Date(new GregorianCalendar(2024, 1, 1));
    Recurrence rec = RecurrenceFactory.createMonthlyRecurrence(startDate);
    Date nextDate = rec.applyRecurrence();
    assertEquals(2024, nextDate.getCalendar().get(Calendar.YEAR));
    assertEquals(Calendar.FEBRUARY, nextDate.getCalendar().get(Calendar.MONTH));
    nextDate = rec.applyRecurrence();
    assertEquals(2024, nextDate.getCalendar().get(Calendar.YEAR));
    assertEquals(Calendar.MARCH, nextDate.getCalendar().get(Calendar.MONTH));
  }
}
