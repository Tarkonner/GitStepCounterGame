package com.example.stepcountergame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView steps;
    private Button upgrade;

    public int score = 0;

    private SensorManager sensorManager;
    private Sensor sensor;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        steps = (TextView) findViewById(R.id.Steps);
        upgrade = (Button) findViewById(R.id.Upgrade);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        steps.setText(String.valueOf(score));

        score = sensor.getFifoReservedEventCount();

        upgrade.setOnClickListener(new View.OnClickListener()
        {
           @Override
           public  void onClick(View v)
           {
                updateScore();
           }
        });
    }

    void updateScore()
    {
        score++;
        steps.setText(String.valueOf(score));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateScore();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        updateScore();
    }
}


