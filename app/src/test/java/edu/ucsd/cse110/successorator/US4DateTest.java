package edu.ucsd.cse110.successorator;


import org.junit.Test;

import static org.junit.Assert.*;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class US4DateTest
{

   /**
    * Test for Date Class
    * */

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
    public void testHourAdvance_PositiveChange() {
        // Arrange
        Calendar initialCalendar = new GregorianCalendar(2024, Calendar.FEBRUARY, 18, 12, 0); // Initial date: 2024-02-18 12:00 PM
        Date date = new Date(initialCalendar);
        // Act
        date.hourAdvance(3); // Advance 3 hours
        // Assert
        Calendar expectedCalendar = new GregorianCalendar(2024, Calendar.FEBRUARY, 18, 15, 0); // Expected date: 2024-02-18 3:00 PM
        assertEquals(expectedCalendar, date.getCalendar());
    }

    @Test
    public void testHourAdvance_NegativeChange() {
        // Arrange
        Calendar initialCalendar = new GregorianCalendar(2024, Calendar.FEBRUARY, 18, 9, 30); // Initial date: 2024-02-18 9:30 AM
        Date date = new Date(initialCalendar);
        // Act
        date.hourAdvance(-2); // Advance backward by 2 hours
        // Assert
        Calendar expectedCalendar = new GregorianCalendar(2024, Calendar.FEBRUARY, 18, 7, 30); // Expected date: 2024-02-18 7:30 AM
        assertEquals(expectedCalendar, date.getCalendar());
    }

    @Test
    public void testHourAdvance_ZeroChange() {
        // Arrange
        Calendar initialCalendar = new GregorianCalendar(2024, Calendar.FEBRUARY, 18, 15, 45); // Initial date: 2024-02-18 3:45 PM
        Date date = new Date(initialCalendar);

        // Act
        date.hourAdvance(0); // No change

        // Assert
        Calendar expectedCalendar = new GregorianCalendar(2024, Calendar.FEBRUARY, 18, 15, 45); // Expected date: 2024-02-18 3:45 PM
        assertEquals(expectedCalendar, date.getCalendar());
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
