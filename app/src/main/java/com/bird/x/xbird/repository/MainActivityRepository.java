package com.bird.x.xbird.repository;

import com.bird.x.xbird.database.FallRecord;

import java.util.List;

public interface MainActivityRepository {
    void storeFreeFallLocally(FallRecord record);

    List<FallRecord> fetchFallListLocally();

}
