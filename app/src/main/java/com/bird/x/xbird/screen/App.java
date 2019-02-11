package com.bird.x.xbird.screen;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

  public static boolean isActivityVisible() {
    return activityVisible;
  }  

  public static void activityResumed() {
    activityVisible = true;
  }

  public static void activityPaused() {
    activityVisible = false;
  }

  private static boolean activityVisible;

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
    RealmConfiguration realmConfig = new RealmConfiguration.Builder()
            .name("freefall.realm")
            .schemaVersion(1)
            .build();
    Realm.setDefaultConfiguration(realmConfig);
  }
}