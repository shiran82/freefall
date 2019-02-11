package com.bird.x.xbird.repository;

import com.bird.x.xbird.database.FallRecord;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

public class MainActivityDataRepository implements MainActivityRepository {
    private Realm realm;

    @Override
    public void storeFreeFallLocally(FallRecord record) {
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(realm -> realm.insertOrUpdate(record));

        realm.close();

    }

    @Override
    public List<FallRecord> fetchFallListLocally() {
        realm = Realm.getDefaultInstance();

        RealmQuery<FallRecord> query = realm.where(FallRecord.class);
        List<FallRecord> records = realm.copyFromRealm(query.findAll());

        realm.close();

        return records;
    }
}
