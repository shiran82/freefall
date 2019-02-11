package com.bird.x.xbird.database;

import io.realm.RealmObject;

public class FallRecord extends RealmObject {
    private long milliseconds;
    private long timeRecorded;

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(long timeRecorded) {
        this.timeRecorded = timeRecorded;
    }
}
