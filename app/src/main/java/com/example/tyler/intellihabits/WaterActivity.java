package com.example.tyler.intellihabits;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;


/**
 * READ THIS
 * GYM ACTIVITY IS USED FOR WATER ACTIVITY. FOR SOME REASON ONE OF MY ACTIVITES KEPT CRASHING SO I COPY PASTED IT
 * INTO GYM ACTIVITY INSTEAD AND IT WORKED??? GYMACTIVITY.JAVA AND ACTIVTY_GYM XML ARE BOTH FOR THE WATER
 * TRACKING APPLICATION.  IT ALLOWS YOU TO DELETE PHOTOS WHEN YOU PRESS ON IT.  I AM GOING TO MAKE ANOTHER GYM ACTIVITY
 * CALLED REAL GYM ACTIVITY. HOPEFULLY THIS WON'T HAPPEN AGAIN?????
 *
 */

import java.util.ArrayList;

public class WaterActivity extends ActionBarActivity {

    private ArrayList<MyImage> images;
    private ImageAdapter imageAdapter;
    private ListView listView;
    private Uri mCapturedImageURI;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private double total_oz = 0;
    private double waterbottlecount = 0;
    private DAOdb mdaOdb;


    private static final String TAG = "WaterActivity";

    public double getTotal_oz(){
        return total_oz;
    }

    public double getWaterbottlecount(){
        return waterbottlecount;
    }
    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, WaterActivity.class);
        return i;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_water);

        // Construct the data source
        images = new ArrayList();
        // Create the adapter to convert the array to views
        imageAdapter = new ImageAdapter(this, images);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(imageAdapter);
        //addItemClickListener(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyImage image = (MyImage) listView.getItemAtPosition(position);
                Intent intent =
                        new Intent(getBaseContext(), GymActivity.class);
                intent.putExtra("IMAGE", (new Gson()).toJson(image));
                Log.d(TAG, "On intent");
                startActivity(intent);
                Log.d(TAG, "On startactivity");


            }
        });
        initDB();
        waterbottlecount = listView.getChildCount();
    }

    private void initDB() {
        mdaOdb = new DAOdb(this);
        //        add images from database to images ArrayList
        for (MyImage mi : mdaOdb.getImages()) {
            images.add(mi);
        }
    }

    public void btnAddOnClick(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setTitle("Enter how much water you're drinking (Oz)");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        final EditText mEditText_water = (EditText) dialog.findViewById(R.id.water_oz);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btnTakePhoto)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        total_oz = total_oz + (double) Integer.parseInt(mEditText_water.getText().toString());
                        waterbottlecount = listView.getChildCount() + 1;
                        activeTakePhoto();
                        dialog.dismiss();
                    }
                });

        // show dialog on screen
        dialog.show();
    }

    /**
     * take a photo
     */
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor =
                    managedQuery(mCapturedImageURI, projection, null,
                            null, null);
            int column_index_data = cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String picturePath = cursor.getString(column_index_data);
            MyImage image = new MyImage();
            image.setTitle("Waterbottle #" + waterbottlecount);
            image.setDescription(
                    "You have consumed " + total_oz + "oz of water!!");
            image.setDatetime(System.currentTimeMillis());
            image.setPath(picturePath);
            images.add(image);
            mdaOdb.addImage(image);
        }
    }


//    private void addItemClickListener(final ListView listView) {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                MyImage image = (MyImage) listView.getItemAtPosition(position);
//                Intent intent =
//                        new Intent(getBaseContext(), DisplayImage.class);
//                intent.putExtra("IMAGE", (new Gson()).toJson(image));
//                Log.d(TAG, "On intent");
//                startActivity(intent);
//                Log.d(TAG, "On startactivity");
//
//            }
//        });
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (mCapturedImageURI != null) {
            outState.putString("mCapturedImageURI",
                    mCapturedImageURI.toString());

        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse(
                    savedInstanceState.getString("mCapturedImageURI"));
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "On start");
    }

    @Override
    public void onPause(){
        super.onPause();
//        Intent i = getIntent();
//        i.putExtra("water_oz",(int)total_oz);
//        setResult(RESULT_OK,i);
//        finish();
    }
}