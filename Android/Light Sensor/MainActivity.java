package com.example.lightsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor brightness;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        text = findViewById(R.id.tv_text);

        setUpSensor();
    }

    private void setUpSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light1 = event.values[0];
            text.setText("Sensor: " + light1 + "\n" + brightness(light1));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    private String brightness(float brightnessValue) {
        if (brightnessValue == 0) {
            return "Pitch black";
        } else if (brightnessValue >= 1 && brightnessValue <= 10) {
            return "Dark";
        } else if (brightnessValue >= 11 && brightnessValue <= 50) {
            return "Grey";
        } else if (brightnessValue >= 51 && brightnessValue <= 5000) {
            return "Normal";
        } else if (brightnessValue >= 5001 && brightnessValue <= 25000) {
            return "Incredibly bright";
        } else {
            return "This light will blind you";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
