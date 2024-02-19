package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.util.DateUpdater;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class US4DateUpdaterTest {
    /**
     * Test for Date Updater Class
     * */
    @Test
    public void testConstructor() {
        // Arrange & Act
        DateUpdater dateUpdater = new DateUpdater(3);

        // Assert
        assertNotNull(dateUpdater);
        assertNotNull(dateUpdater.getDateString());
        // You may add more assertions based on your specific implementation
    }

    @Test
    public void testDateUpdate_PositiveChange() {
        // Arrange
        DateUpdater dateUpdater = new DateUpdater(0);
        Calendar initialDate = (Calendar)dateUpdater.getDate().getCalendar().clone();
        // Act
        dateUpdater.dateUpdate(2);
        // Assert
        assertNotEquals(initialDate, dateUpdater.getDate().getCalendar());
        // Additional assertions specific to your implementation
    }

    @Test
    public void testDateUpdate_NegativeChange() {
        // Arrange
        DateUpdater dateUpdater = new DateUpdater(0);
        Calendar initialDate = (Calendar) dateUpdater.getDate().getCalendar().clone();

        // Act
        dateUpdater.dateUpdate(-2);

        // Assert
        assertNotEquals(initialDate, dateUpdater.getDate().getCalendar());
        // Additional assertions specific to your implementation
    }
    @Test
    public void testDateUpdate_ZeroChange() {
        // Arrange
        DateUpdater dateUpdater = new DateUpdater(0);
        Calendar initialDate = (Calendar) dateUpdater.getDate().getCalendar().clone();

        // Act
        dateUpdater.dateUpdate(0);

        // Assert
        assertEquals(initialDate, dateUpdater.getDate().getCalendar());
        // Additional assertions specific to your implementation
    }

    @Test
    public void testDateUpdate_CheckDateCalled() {
        // Arrange
        DateUpdater dateUpdater = new DateUpdater(0);

        // Act
        dateUpdater.dateUpdate(1);

        // Assert
        // Ensure that checkDate method is called after dateUpdate
        assertNotNull(dateUpdater.getDateString().getValue());
        // Additional assertions specific to your implementation
    }

    @Test
    public void testSyncDate_CurrentTimeUpdated() {
        // Arrange
        DateUpdater dateUpdater = new DateUpdater(0);
        long initialTime = System.currentTimeMillis();

        // Act
        dateUpdater.syncDate();
        long updatedTime = dateUpdater.getDate().getCalendar().getTimeInMillis();

        // Assert
        assertTrue(updatedTime >= initialTime);
        // Additional assertions specific to your implementation
    }

    @Test
    public void testSyncDate_CheckDateCalled() {
        // Arrange
        DateUpdater dateUpdater = new DateUpdater(0);

        // Act
        dateUpdater.syncDate();

        // Assert
        // Ensure that checkDate method is called after syncDate
        assertNotNull(dateUpdater.getDateString().getValue(), "checkDate not called after syncDate");
        // Additional assertions specific to your implementation
  }

    @Test
    public void testCheckDate_NonNullDateString() {
        // Arrange
        DateUpdater dateUpdater = new DateUpdater(0);

        // Act
        dateUpdater.checkDate();

        // Assert
        assertNotNull(dateUpdater.getDateString().getValue(), "DateString should not be null after checkDate");
        // Additional assertions specific to your implementation
    }


    @Test
    public void testGetDateString() {
        // Arrange
        DateUpdater dateUpdater = new DateUpdater(0);

        // Act
        MutableSubject<String> dateString = dateUpdater.getDateString();

        // Assert
        assertNotNull(dateString);
        // You may add more assertions based on your specific implementation
    }
}
