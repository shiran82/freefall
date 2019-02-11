package com.bird.x.xbird.screen;

import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.bird.x.xbird.R;

import com.bird.x.xbird.adapter.FallRecyclerViewAdapter;
import com.bird.x.xbird.databinding.ActivityMainBinding;
import com.bird.x.xbird.model.Fall;
import com.bird.x.xbird.presenter.MainActivityPresenter;
import com.bird.x.xbird.repository.MainActivityDataRepository;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements SensorEventListener, MainActivityMVP {
    private SensorManager manager;
    private Sensor accelerometer;
    private boolean freeFall = false;
    private long startTime;
    private ActivityMainBinding binding;
    private FallRecyclerViewAdapter adapter;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        adapter = new FallRecyclerViewAdapter();

        Realm.init(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        setSupportActionBar(binding.toolbar.viewToolbar);

        presenter = new MainActivityPresenter(new MainActivityDataRepository(), this);

        presenter.fetchFallList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.activityResumed();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x, y, z;

            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            double a = Math.sqrt(x * x + y * y + z * z);

            presenter.checkFall(a, freeFall, sensorEvent.timestamp, startTime);
        }
    }

    @Override
    public void showToast(long durationInMS) {
        if (App.isActivityVisible()) {
            String message = String.format(Locale.US, getString(R.string.toast_text)
                            .replace("{placeholder}", "%s"), durationInMS);

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        App.activityPaused();
    }

    @Override
    public void showFall(Fall fall) {
        adapter.addItem(fall);
        binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void showFalls(List<Fall> falls) {
        adapter.addItems(falls);
    }

    @Override
    public void freeFallState(boolean freeFall, long timestamp) {
        this.freeFall = freeFall;
        this.startTime = timestamp;
    }

    @Override
    protected void onDestroy() {
        manager.unregisterListener(this);
        super.onDestroy();
    }
}
