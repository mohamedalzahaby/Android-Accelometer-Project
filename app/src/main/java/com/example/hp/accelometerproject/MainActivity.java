package com.example.hp.accelometerproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Map<Character, ArrayList<Float>> Points;
    ArrayList<Float> x;
    ArrayList<Float> y;
    ArrayList<Float> z;
    Sensor accelerometer;
    SensorManager sm;
    TextView acceleration;
    TextView acceleration2;
    FirebaseDatabase database;
    DatabaseReference myRefx;
    DatabaseReference myRefy;
    DatabaseReference myRefz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // give instance of database
        database = FirebaseDatabase.getInstance();

        Points = new HashMap<Character, ArrayList<Float>>();
        x = new ArrayList<Float>();
        y = new ArrayList<Float>();
        z = new ArrayList<Float>();

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        acceleration = (TextView) findViewById(R.id.acceleration);
        acceleration2 = (TextView) findViewById(R.id.acceleration2);


        Log.d("project", "onCreate 2");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // enter arrayLists to firebase
                myRefx = database.getReference("x");
                myRefx.setValue(x);
                myRefy = database.getReference("y");
                myRefy.setValue(y);
                myRefz = database.getReference("z");
                myRefz.setValue(z);
//note

                Toast.makeText(MainActivity.this,"doneeeeee" , Toast.LENGTH_SHORT).show();


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void test(String textBody)
    {
        Toast.makeText(MainActivity.this,"here we go" , Toast.LENGTH_SHORT).show();
        try{
            FileOutputStream stream = openFileOutput("C:\\ddd.txt" ,Context.MODE_APPEND);
            stream.write("textBody".getBytes());
            stream.close();
            Toast.makeText(MainActivity.this,"saved" , Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"errorrrrr" , Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        acceleration.setText("X: "+event.values[0]+"\nY: "+event.values[1]+"\nZ: "+event.values[2]);

        x.add(event.values[0]);
        y.add(event.values[1]);
        z.add(event.values[2]);
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
