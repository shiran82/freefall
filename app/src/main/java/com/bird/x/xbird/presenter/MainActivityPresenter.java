package com.bird.x.xbird.presenter;

import com.bird.x.xbird.database.FallRecord;
import com.bird.x.xbird.model.Fall;
import com.bird.x.xbird.repository.MainActivityRepository;
import com.bird.x.xbird.screen.MainActivityMVP;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivityPresenter {
    private MainActivityRepository repository;
    private MainActivityMVP mvp;
    private final static int LOW_THRESHOLD = 1;
    private final static int UPPER_THRESHOLD = 10;

    public MainActivityPresenter(MainActivityRepository repository, MainActivityMVP mvp) {
        this.repository = repository;
        this.mvp = mvp;
    }

    public void fetchFallList() {
        List<FallRecord> records = repository.fetchFallListLocally();

        if (!records.isEmpty()) {
            List<Fall> falls = new ArrayList<>();

            for (FallRecord record : records) {
                Fall fall = new Fall();
                fall.setMilliseconds(record.getMilliseconds());
                fall.setTimeRecorded(record.getTimeRecorded());

                falls.add(fall);
            }

            mvp.showFalls(falls);
        }
    }

    public void checkFall(double a, boolean freeFall, long timestamp, long startTime) {
        if (a < LOW_THRESHOLD) {
            if (!freeFall) {
                mvp.freeFallState(true, timestamp);
            }
        } else if (a > UPPER_THRESHOLD){
            if (freeFall) {
                mvp.freeFallState(false, timestamp);
                long duration = timestamp - startTime;

                long durationInMS = TimeUnit.MILLISECONDS.convert(duration, TimeUnit
                        .NANOSECONDS);

                mvp.showToast(durationInMS);

                Fall fall = new Fall();
                fall.setMilliseconds(durationInMS);
                fall.setTimeRecorded(System.currentTimeMillis());

                saveFall(fall);
            }
        }
    }

    private void saveFall(com.bird.x.xbird.model.Fall fall) {
        FallRecord record = new FallRecord();
        record.setMilliseconds(fall.getMilliseconds());
        record.setTimeRecorded(fall.getTimeRecorded());

        repository.storeFreeFallLocally(record);

        mvp.showFall(fall);
    }
}
