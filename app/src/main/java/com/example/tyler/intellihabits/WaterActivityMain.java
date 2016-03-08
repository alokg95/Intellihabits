package com.example.tyler.intellihabits;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WaterActivityMain extends AppCompatActivity {

    Button mStartButton;
    Button mStopButton;
    Button mWaterButton;
    TextView mWaterText;
    WaterActivity mWaterActivity;
    private PendingIntent pendingIntent;
    double total_oz;

    private static final int OPEN_WATER_ACTIVITY = 2;



    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, WaterActivityMain.class);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_activity_main);


        Intent alarmIntent = new Intent(WaterActivityMain.this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(WaterActivityMain.this, 0, alarmIntent, 0);

        mStartButton = (Button)findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();

            }
        });
        mStopButton = (Button)findViewById(R.id.stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        mWaterButton = (Button)findViewById(R.id.picture_button);
        mWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = WaterActivity.newIntent(WaterActivityMain.this);
                startActivityForResult(i, OPEN_WATER_ACTIVITY);
            }
        });
        mWaterText = (TextView)findViewById(R.id.water_text);
        mWaterActivity = new WaterActivity();
//        Toast.makeText(this,Integer.toString((int)mWaterActivity.total_oz),Toast.LENGTH_SHORT).show();
//
//
//        mWaterText.setText("Today, you have consumed "+mWaterActivity.total_oz+" of water!!!!");
        }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == OPEN_WATER_ACTIVITY && resultCode == RESULT_OK){
//            total_oz= data.getExtras().getInt(("water_oz"));
//                mWaterText.setText("Today, you have consumed " + mWaterActivity.total_oz + " of water!!!!");
//        }
//    }

    public void start(){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval,pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();

    }
    public void cancel(){
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this,"Alarm Cancel",Toast.LENGTH_SHORT).show();

    }



}
