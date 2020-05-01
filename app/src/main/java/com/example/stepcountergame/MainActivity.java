package com.example.stepcountergame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Things in scene
    private TextView steps;
    private Button upgrade;
    private TextView multiText;
    private TextView upgradeText;

    //Sensor
    private SensorManager sensorManager;
    private Sensor sensor;

    //Variabel
    private boolean runnig = false;
    private String mul = "Multiply: ";

    //Save data
    private static String SHARD_PREFS = "shardPrefs";
    private Handler timerHandler; //Timer for save game
    private int howOftenToSave = 1000;

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

        timerHandler = new Handler();
        timerHandler.postDelayed(updateTimerThread, 0);

        //Load game
        loadData();



        //Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        //Show start stats
        upgradeText.setText(String.valueOf(pointsToGive * costPerMultiply));
        multiText.setText(mul + pointsToGive);
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
            multiText.setText(mul + pointsToGive);

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


    //https://www.youtube.com/watch?v=fJEFZ6EOM9o
    void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARD_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Score", score);
        editor.putInt("PointsToGive", pointsToGive);

        editor.apply();
    }

    void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARD_PREFS, MODE_PRIVATE);

        score = sharedPreferences.getInt("Score", 0);
        pointsToGive = sharedPreferences.getInt("PointsToGive", 1);
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            saveData();
            timerHandler.postDelayed(this, howOftenToSave);
        }
    };

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

