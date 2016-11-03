package com.egco438.a13273;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.egco438.a13273.Fortune;
import com.egco438.a13273.MainActivity;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import a13273.egco438.com.a13273.R;

public class NewFortune extends AppCompatActivity implements SensorEventListener {
    public static final int DETAIL_REQ_CODE = 1001;
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;

    public static final String Message = "Fortune Result";
    public static final String Pic_name= "Pictures Name";
    public static final String Time = "Time";

    ImageButton imgButton;
    TextView txt;
    boolean check= false;
    int count=0;

    private String[] results_message = {"You're lucky","Don't Panic","You will get A","Something surprise you today","Work harder"};
    TextView result;
    TextView date;
    ImageView pic;

    Random aa = new Random();
    int ran = aa.nextInt(5);
    String pos = "pic"+ran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fortune_new);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        txt = (TextView)findViewById(R.id.sensor);
        result = (TextView)findViewById(R.id.result);
        date = (TextView)findViewById(R.id.date);
        pic = (ImageView)findViewById(R.id.imageView2);

        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                check=true;
                if(txt.getText()=="Save"){
                    Fortune fortune = new Fortune((long)0,result.getText().toString(),pos, date.getText().toString());
                    sendData(fortune);
                }
            }
        });
    }

    public void sendData(Fortune fortune){

        Intent intent = new Intent();
        intent.putExtra(Message, fortune.getMessage());
        intent.putExtra(Pic_name, fortune.getPicName());
        intent.putExtra(Time, fortune.getTimestamp());
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometer(SensorEvent event){
        if (check) {
            float[] values = event.values;
            // Movement
            float x = values[0];
            float y = values[1];
            float z = values[2];

            float accelationSquareRoot = (x * x + y * y + z * z)
                    / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH); //distance between 2 point
            long actualTime = System.currentTimeMillis();
            if (accelationSquareRoot >= 4) //
            {
                if (actualTime - lastUpdate < 500) {
                    return;
                }
                count = count+1;
                lastUpdate = actualTime;
                txt.setText("Shaking");

            }

            if(count==3){
                count =4;
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                result.setText(results_message[ran]);
                int res = getResources().getIdentifier(pos,"drawable",getPackageName());
                pic.setImageResource(res);
                date.setText(currentDateTimeString);
                txt.setText("Save");
                check=false;

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, DETAIL_REQ_CODE);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
