package com.example.tyler.intellihabits;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TrackerActivity extends AppCompatActivity {
    private static final String TAG = "TrackerActivity";

    public static Intent newIntent(Context packageContext){
        Intent i= new Intent(packageContext,TrackerActivity.class);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        Toast.makeText(getApplicationContext(),"tracker",Toast.LENGTH_SHORT).show();
    }

//    public ArrayList <Double> getLocation()
//    {
//        // Get the location manager
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        String bestProvider = locationManager.getBestProvider(criteria, false);
//        Location location = locationManager.getLastKnownLocation(bestProvider);
//        Double lat,lon;
//        ArrayList<Double> aList = new ArrayList<>();
//        try {
//            lat = location.getLatitude ();
//            lon = location.getLongitude();
//            aList.add(lat);
//            aList.add(lon);
//        }
//        catch (NullPointerException e){
//            e.printStackTrace();
//            return null;
//        }
//        return aList;
//    }

}
