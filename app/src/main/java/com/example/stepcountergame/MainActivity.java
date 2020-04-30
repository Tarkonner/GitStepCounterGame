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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView steps;
    private Button upgrade;
    private TextView multiText;
    private TextView upgradeText;



    private SensorManager sensorManager;
    private Sensor sensor;
    private boolean runnig = false;

    //Variabler to change
    public int score = 0;
    private  int pointsToGive = 1;
    private int costPerMultiply = 30;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup
        steps = (TextView) findViewById(R.id.Steps);
        multiText = (TextView) findViewById(R.id.Multi);
        upgradeText = (TextView) findViewById(R.id.UpgradeText);
        upgrade = (Button) findViewById(R.id.Upgrade);


        //Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        //Show start stats
        upgradeText.setText(String.valueOf(pointsToGive * costPerMultiply));
        multiText.setText("Multiply: " + pointsToGive);
        steps.setText(String.valueOf(score));

        //Start the Step Counter
        onResume();


        upgrade.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View v)
            {
                pointsUpgrade();
            }
        });
    }

    void pointsUpgrade()
    {
        int cost = pointsToGive * costPerMultiply;

        if(score >= cost)
        {
            score -= cost;
            pointsToGive++;
            steps.setText(String.valueOf(score));
            multiText.setText("Multiply: " + pointsToGive);

            int newCost = pointsToGive * costPerMultiply;
            upgradeText.setText(String.valueOf(newCost));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onResume()
    {
        super.onResume();
        runnig = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null)
        {
            sensorManager.registerListener(this, countSensor, sensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onPause(){
        super.onPause();
        runnig = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(runnig)
        {
            score += pointsToGive;
            //steps.setText(String.valueOf(event.values[0]));
            steps.setText(String.valueOf(score));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

