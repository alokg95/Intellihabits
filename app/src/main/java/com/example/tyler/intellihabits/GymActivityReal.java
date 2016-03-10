package com.example.tyler.intellihabits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class GymActivityReal extends AppCompatActivity {

    TextView trackr;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, GymActivityReal.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_activity_real);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        trackr = (TextView) findViewById(R.id.tracker_textView);
        trackr.setText(Html.fromHtml("Visit the <a href=\"https://www.google.com/maps/place/UCSB+Recreational+Sports/@34.4183004,-119.8507967,17z/data=!3m1!4b1!4m2!3m1!1s0x80e93f65175543a1:0x93574af0a21e0e70\">gym!</a>"));
        trackr.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
