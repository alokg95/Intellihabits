package com.example.tyler.intellihabits;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private Button mWaterButton;
    private Button mGymButton;
    private Button mStudyButton;
    private Button mTrackerButton;
    private TextView study_text;
    private TextView water_text;

    private int totalHoursStudied;
    private int totalMinutesStudied;
    private String TAG = "StudyActivity";
    StudyActivity sa = new StudyActivity();
    WaterActivity wa = new WaterActivity();

    private static final int REQUEST_CODE_WATER = 1;
    private static final int REQUEST_CODE_GYM = 2;
    private static final int REQUEST_CODE_STUDY = 3;
    private static final int REQUEST_CODE_TRACKER = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("Inside home", "Inside Home");
        study_text = (TextView) findViewById(R.id.study_textview);
        water_text = (TextView) findViewById(R.id.water_textview);
        totalHoursStudied = sa.totalHoursStudied;
        totalMinutesStudied = sa.totalMinutesStudied;
        Log.d("Home", " " + totalHoursStudied + " " + totalMinutesStudied);
        String study_summary = "Studied " + totalHoursStudied + " hours, " + totalMinutesStudied + " minute(s)";
        String water_summary = "Drank " + wa.total_oz + " ounces of water";
        water_text.setText(water_summary);
        study_text.setText(study_summary);



        mWaterButton=(Button)findViewById(R.id.water);
        mWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = WaterActivityMain.newIntent(HomeActivity.this);
                startActivityForResult(i, REQUEST_CODE_WATER);


            }
        });
        mGymButton=(Button)findViewById(R.id.gym);
        mGymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=GymActivityReal.newIntent(HomeActivity.this);
                startActivityForResult(i,REQUEST_CODE_GYM);
            }
        });
        mStudyButton=(Button)findViewById(R.id.study);
        mStudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=StudyActivity.newIntent(HomeActivity.this);
                startActivityForResult(i,REQUEST_CODE_STUDY);
            }
        });
        mTrackerButton=(Button)findViewById(R.id.tracker);
        mTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=TrackerActivity.newIntent(HomeActivity.this);
                startActivityForResult(i,REQUEST_CODE_TRACKER);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired in " + TAG);
        study_text = (TextView) findViewById(R.id.study_textview);
        water_text = (TextView) findViewById(R.id.water_textview);
        totalHoursStudied = sa.totalHoursStudied;
        totalMinutesStudied = sa.totalMinutesStudied;
        Log.d("Home", " " + totalHoursStudied + " " + totalMinutesStudied);
        String study_summary = "Studied " + totalHoursStudied + " hours, " + totalMinutesStudied + " minute(s)";
        study_text.setText(study_summary);
        String water_summary = "Drank " + wa.total_oz + " ounces of water";
        water_text.setText(water_summary);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume fired ............");
        study_text = (TextView) findViewById(R.id.study_textview);
        water_text = (TextView) findViewById(R.id.water_textview);
        totalHoursStudied = sa.totalHoursStudied;
        totalMinutesStudied = sa.totalMinutesStudied;
        Log.d("Home", " " + totalHoursStudied + " " + totalMinutesStudied);
        String study_summary = "Studied " + totalHoursStudied + " hours, " + totalMinutesStudied + " minute(s)";
        study_text.setText(study_summary);
        String water_summary = "Drank " + wa.total_oz + " ounces of water";
        water_text.setText(water_summary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
