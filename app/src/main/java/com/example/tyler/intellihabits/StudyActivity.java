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
import java.util.Calendar;
import java.util.Locale;

public class StudyActivity extends AppCompatActivity {

    private static final String TAG = "StudyActivity";
    public static boolean studySessionToday = false; // Set false initially. Once the user has a study sesh, we'll mark it true.
    public static int hoursStudied = 0;
    public static int beginStudySession;
    public static int endStudySession;
    Button btnStudy;



    public static Intent newIntent(Context packageContext){
        Intent i= new Intent(packageContext,StudyActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate fired in " + TAG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        btnStudy = (Button) findViewById(R.id.btnStudy);
        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                modifyStudySession();
            }
        });

    }

    public void modifyStudySession(){
//        Calendar calendar = Calendar.getInstance(Locale.getDefault());
//        int minute = calendar.get(Calendar.MINUTE);
        if(btnStudy.getText().toString().equals("Begin Study Session")){
            beginStudySession();
        } else {
            endStudySession();
        }
    }

    public void beginStudySession(){
        Log.d(TAG, "Inside beginStudySesh");
        changeButtonState();
    }

    public void endStudySession(){
        Log.d(TAG, "Inside endStudySesh");
        changeButtonState();
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
        hoursStudied++;
        Log.d(TAG, "Hours Studied Incremented!: " + hoursStudied);
    }

}
