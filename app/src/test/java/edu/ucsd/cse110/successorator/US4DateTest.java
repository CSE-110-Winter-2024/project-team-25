package edu.ucsd.cse110.successorator;


import org.junit.Test;

import static org.junit.Assert.*;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class US4DateTest
{

  @Test
  public void testDayIncrement() {
    // Create a calendar with a specific date
    Calendar calendar = new GregorianCalendar(2024, Calendar.FEBRUARY, 18);
    Date date = new Date(calendar);

    // Increment the day and check if it's correct
    date.dayIncrement();
    assertEquals(19, date.getCalendar().get(Calendar.DAY_OF_MONTH));
  }

  @Test
  public void testDayIncrementDayEdgeCase() {
    // Create a calendar with a specific date
    Calendar calendar = new GregorianCalendar(2024, Calendar.FEBRUARY, 29);
    Date date = new Date(calendar);

    // Increment the day and check if it's correct
    date.dayIncrement();
    assertEquals(1, date.getCalendar().get(Calendar.DAY_OF_MONTH));
  }
  @Test
  public void testDayIncrementMonthEdgeCase() {
    // Create a calendar with a specific date
    Calendar calendar = new GregorianCalendar(2024, Calendar.DECEMBER, 31);
    Date date = new Date(calendar);

    // Increment the day and check if it's correct
    date.dayIncrement();
    assertEquals(1, date.getCalendar().get(Calendar.DAY_OF_MONTH));
  }

  @Test
  public void testEquals() {
    // Create two Date objects with the same calendar date
    Calendar calendar1 = new GregorianCalendar(2024, Calendar.FEBRUARY, 18);
    Date date1 = new Date(calendar1);
    Calendar calendar2 = new GregorianCalendar(2024, Calendar.FEBRUARY, 18);
    Date date2 = new Date(calendar2);

    // Ensure that they are equal
    assertEquals(date1, date2);
  }

  @Test
  public void testNotEquals() {
    // Create two Date objects with different calendar dates
    Calendar calendar1 = new GregorianCalendar(2024, Calendar.FEBRUARY, 18);
    Date date1 = new Date(calendar1);
    Calendar calendar2 = new GregorianCalendar(2024, Calendar.FEBRUARY, 19);
    Date date2 = new Date(calendar2);

    // Ensure that they are not equal
    assertNotEquals(date1, date2);
  }

  @Test
  public void testHashCode() {
    // Create two Date objects with the same calendar date
    Calendar calendar1 = new GregorianCalendar(2024, Calendar.FEBRUARY, 18);
    Date date1 = new Date(calendar1);
    Calendar calendar2 = new GregorianCalendar(2024, Calendar.FEBRUARY, 18);
    Date date2 = new Date(calendar2);

    // Ensure that their hash codes are equal
    assertEquals(date1.hashCode(), date2.hashCode());
  }

  @Test
  public void testHashCodeDifferentDates() {
    // Create two Date objects with different calendar dates
    Calendar calendar1 = new GregorianCalendar(2024, Calendar.FEBRUARY, 18);
    Date date1 = new Date(calendar1);
    Calendar calendar2 = new GregorianCalendar(2024, Calendar.FEBRUARY, 19);
    Date date2 = new Date(calendar2);

    // Ensure that their hash codes are not equal
    assertNotEquals(date1.hashCode(), date2.hashCode());
  }
}
