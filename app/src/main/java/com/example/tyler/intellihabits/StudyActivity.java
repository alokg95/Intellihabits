package com.example.tyler.intellihabits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StudyActivity extends AppCompatActivity {

    private static final String TAG = "StudyActivity";
    public static boolean studySessionToday = false; // Set false initially. Once the user has a study sesh, we'll mark it true.
    public static int totalHoursStudied = 0;
    public static int totalMinutesStudied = 0;
    public static int beginStudySession;
    public static int endStudySession;
    public static String startTime = "";
    public static String endTime = "";
    Button btnStudy;
    TextView studyText;



    public static Intent newIntent(Context packageContext){
        Intent i= new Intent(packageContext,StudyActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate fired in " + TAG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        studyText = (TextView) findViewById(R.id.textView);
        btnStudy = (Button) findViewById(R.id.btnStudy);
        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                modifyStudySession();
            }
        });

        if(studyText.getText().toString().equals("0 hours studied today")){
            studyText.setText("You've studied exactly 0 hours today. Get studying!");
        }

    }

    public void modifyStudySession(){
        if(btnStudy.getText().toString().equals("Begin Study Session")){
            beginStudySession();
        } else {
            endStudySession();
        }
    }

    public void beginStudySession(){
        changeButtonState();
        DateFormat df = new SimpleDateFormat("HH:mm");
        startTime = df.format(Calendar.getInstance().getTime());


    }

    public void endStudySession(){
        changeButtonState();
        DateFormat df = new SimpleDateFormat("HH:mm");
        endTime = df.format(Calendar.getInstance().getTime());
        updateHoursStudied(startTime, endTime);
    }

    public void updateHoursStudied(String startTime, String endTime){
        int startMinutes;
        int endMinutes;
        int startHour = 0;
        int endHour = 0;
        int hoursStudied = 0;
        int minutesStudied = 0;

        if(startTime.charAt(0) == endTime.charAt(0) && startTime.charAt(1) == endTime.charAt(1)) {
            // They are within the same hour

            startMinutes = Integer.parseInt(startTime.substring(3,startTime.length()));
            endMinutes = Integer.parseInt(endTime.substring(3,startTime.length()));

        } else {
            // They are within different hours. We'll have to calculate the difference

            startHour = Integer.parseInt(startTime.substring(0,2));
            endHour = Integer.parseInt(endTime.substring(0,2));
            startMinutes = Integer.parseInt(startTime.substring(3,startTime.length()));
            endMinutes = Integer.parseInt(endTime.substring(3,startTime.length()));

        }
        hoursStudied = Math.abs(endHour - startHour);
        minutesStudied = Math.abs(endMinutes - startMinutes);
        totalHoursStudied += hoursStudied;
        totalMinutesStudied += minutesStudied;

        studyText.setText("Studied " + totalHoursStudied + " hours, " + totalMinutesStudied + " minute(s)");

    }

    public void changeButtonState(){
        if(btnStudy.getText().toString().equals("Begin Study Session")) {
            btnStudy.setText("End Study Session");
        } else {
            btnStudy.setText("Begin Study Session");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired in " + TAG);
    }

}
