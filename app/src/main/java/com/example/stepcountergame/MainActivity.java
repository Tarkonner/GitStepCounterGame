package com.example.stepcountergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
    private SensorManager sensor;

    public int score = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        steps = (TextView) findViewById(R.id.Steps);
        upgrade = (Button) findViewById(R.id.Upgrade);
        sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);



        steps.setText(String.valueOf(score));

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

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}


