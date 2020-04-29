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
    private Button click;
    private TextView multiText;
    private TextView upgradeText;

    public int score = 0;

    private SensorManager sensorManager;
    private Sensor sensor;

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
        click = (Button) findViewById(R.id.Click);


        //Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        //Show start stats
        upgradeText.setText(String.valueOf(pointsToGive * costPerMultiply));
        multiText.setText("Multiply: " + String.valueOf(pointsToGive));
        steps.setText(String.valueOf(score));

        

        click.setOnClickListener(new View.OnClickListener()
        {
           @Override
           public  void onClick(View v)
           {
               updateScore();
           }
        });

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
            multiText.setText("Multiply: " + String.valueOf(pointsToGive));

            int newCost = pointsToGive * costPerMultiply;
            upgradeText.setText(String.valueOf(newCost));
        }
    }

    void updateScore()
    {
        score += pointsToGive;
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


