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
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private Button mWaterButton;
    private Button mGymButton;
    private Button mStudyButton;
    private Button mTrackerButton;

    private static final int REQUEST_CODE_WATER = 0;
    private static final int REQUEST_CODE_GYM = 1;
    private static final int REQUEST_CODE_STUDY = 2;
    private static final int REQUEST_CODE_TRACKER = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
        Log.d("Inside home", "Inside Home");

        mWaterButton=(Button)findViewById(R.id.water);
        mWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = WaterActivity.newIntent(HomeActivity.this);
                startActivityForResult(i, REQUEST_CODE_WATER);

            }
        });
        mGymButton=(Button)findViewById(R.id.gym);
        mGymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=GymActivity.newIntent(HomeActivity.this);
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
                Intent i=StudyActivity.newIntent(HomeActivity.this);
                startActivityForResult(i,REQUEST_CODE_TRACKER);

            }
        });
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
