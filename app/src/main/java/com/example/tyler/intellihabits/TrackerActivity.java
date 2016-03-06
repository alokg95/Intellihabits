package com.example.tyler.intellihabits;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrackerActivity extends Activity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "TrackerActivity";
    private static  final long  INTERVAL = 1000 * 10*60;
    private static  final long  FASTEST_INTERVAL = 1000 * 10;

    Button btnFusedLocation;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;

    Vibrator v;
    private float lat_old;
    private float long_old;
    private float distance = 0;
    private int counter = 0;
    private int counter_amount = 5;
    private int timeinterval= 6* 10 * 1000;
    float [] distance_and = {0};
    int distance_thresh = 50;
    EditText mEditTextInterval;
    Button mButtonUpdate;
    AlertDialog dialog;
    private int onCreateCounter = 0;

    public static Intent newIntent(Context packageContext){
        Intent i= new Intent(packageContext,TrackerActivity.class);
        return i;
    }

    public void buildDialogCheck(){
        final CharSequence[] items = {"WaterBottle","Wallet","Keys"};
        final ArrayList selectedItems = new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check the items you have with you");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            selectedItems.add(which);
                        }
                        else if (selectedItems.contains(which)){
                            selectedItems.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog = builder.create();

    }

    public static float distFrom(float lat1, float lng1, float lat2, float lng2){
        double earthRadius = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        //mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ...............................");
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        buildDialogCheck();
        if (onCreateCounter==0) {
            onCreateCounter = onCreateCounter + 1;
            createLocationRequest();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        setContentView(R.layout.activity_tracker);
        v= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        btnFusedLocation = (Button) findViewById(R.id.btnShowLocation);
        btnFusedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                updateUI();
                createLocationRequest();
            }
        });

        mEditTextInterval = (EditText) findViewById(R.id.timeinterval_text);

        mButtonUpdate = (Button) findViewById(R.id.update_button);
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextInterval.getText().toString() !=null) {
                    timeinterval = Integer.parseInt(mEditTextInterval.getText().toString()) * 60 * 1000;
                    counter_amount = (int) timeinterval / (int) INTERVAL;
                    counter = 0;
                    createLocationRequest();
                    updateUI();
                }
            }
        });




    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.d(TAG, "onStart fired ..............");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;

        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");


        if (lat_old != 0 && long_old !=0) {

            //distance = distFrom(lat_old, long_old, (float) location.getLatitude(), (float) location.getLongitude());
            Location.distanceBetween((double)lat_old,(double)long_old,(double)location.getLatitude(),(double)location.getLongitude(),distance_and);
            distance=distance_and[0];
            //distance = Math.abs(distance);

            if (distance < distance_thresh) {
                counter = counter + 1;
                lat_old = (float) location.getLatitude();
                long_old = (float) location.getLongitude();
                updateUI();

            }
            else if (distance > distance_thresh && counter < counter_amount ){
                updateUI();
                counter = 0;
                lat_old = (float) location.getLatitude();
                long_old = (float) location.getLongitude();


            }
            else if (distance > distance_thresh && counter >= counter_amount ){
                mCurrentLocation = location;
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateUI();
                counter = 0;
                distance = 0;
                lat_old = (float) location.getLatitude();
                long_old = (float) location.getLongitude();
                v.vibrate(400);
                dialog.show();

            }

        }
        if (lat_old ==0 && long_old ==0) {
            lat_old = (float) location.getLatitude();
            long_old = (float) location.getLongitude();
            mCurrentLocation = location;
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateUI();
        }


//        mCurrentLocation = location;
//        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//        updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            tvLocation.setText("At Time: " + mLastUpdateTime + "\n" +
                    "Latitude: " + lat + "\n" +
                    "Longitude: " + lng + "\n" +
                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
                    "Provider: " + mCurrentLocation.getProvider() + "\n" +
                    "Distance:"  + distance + "\n" +
                    "Counter:" + counter + "\n" +
                    "Counter Amount: " + counter_amount);
        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }
}
