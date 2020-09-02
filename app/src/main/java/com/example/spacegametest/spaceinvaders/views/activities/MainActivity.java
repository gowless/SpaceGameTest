package com.example.spacegametest.spaceinvaders.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spacegametest.spaceinvaders.views.MainGameView;
import com.example.spaceinvaders.R;
import com.example.spaceinvaders.views.MainGameView;
import com.example.spaceinvaders.views.Scores;


public class MainActivity extends Activity implements View.OnClickListener {


    //image button
    private Button buttonPlay;
    // the high score button
    private Button buttonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        //getting the button
        buttonPlay = findViewById(R.id.buttonPlay);

        //initializing the highscore button
        buttonScore =  findViewById(R.id.buttonScore);

        //setting the on click listener to high score button
        buttonScore.setOnClickListener(this);
        //setting the on click listener to play now button
        buttonPlay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v==buttonPlay){

            //the transition from MainActivity to GameActivity
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        }
        if(v==buttonScore){
            //the transition from MainActivity to HighScore activity
            startActivity(new Intent(MainActivity.this, Scores.class));
        }


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MainGameView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }



}