package edu.ucsd.cse110.successorator.db;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.ucsd.cse110.successorator.lib.domain.Recurrence;

public class RecurrenceConverters {

    private RecurrenceConverters() {}
    @TypeConverter
    public static byte[] fromRecurrence(Recurrence recurrence) {
        if(recurrence == null) {
            return null;
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(recurrence);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static Recurrence toRecurrence(byte[] data) {
        if(data == null) {
            return null;
        }
        try {
            return (Recurrence) new ObjectInputStream(new ByteArrayInputStream(data)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
