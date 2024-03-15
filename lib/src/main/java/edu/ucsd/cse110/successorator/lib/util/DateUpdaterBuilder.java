package edu.ucsd.cse110.successorator.lib.util;

public class DateUpdaterBuilder {
    private int delayHour;
    private DateFormatter formatter;
    private boolean syncDate;

    public DateUpdaterBuilder() {
        this.delayHour = 0;
        this.formatter = new DateFormatter();
        this.syncDate = false;
    }

    public DateUpdaterBuilder withDelayHour(int delayHour) {
        this.delayHour = delayHour;
        return this;
    }

    public DateUpdaterBuilder withDateFormatter(DateFormatter formatter) {
        this.formatter = formatter;
        return this;
    }

    public DateUpdaterBuilder withSyncDate(boolean syncDate) {
        this.syncDate = syncDate;
        return this;
    }

    public DateUpdater build() {
        DateUpdater dateUpdater = new DateUpdater(delayHour);
        dateUpdater.setFormatter(formatter);

        if (syncDate) {
            dateUpdater.syncDate();
        }

        return dateUpdater;
    }
}