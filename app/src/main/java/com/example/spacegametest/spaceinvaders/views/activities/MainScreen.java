package com.example.spacegametest.spaceinvaders.views.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.spacegametest.R;
import com.example.spacegametest.spaceinvaders.call.ApiClient;
import com.example.spacegametest.spaceinvaders.call.ApiInterface;
import com.example.spacegametest.spaceinvaders.models.Example;
import com.example.spacegametest.spaceinvaders.views.MainGameView;
import com.example.spacegametest.spaceinvaders.views.progressdialog.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreen extends AppCompatActivity {

    Context context;
    //declaring elements
    Button button, button_page, erase;
    //declaring WebView
    WebView webView;



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //initializing prefs
        final SharedPreferences sharedPreferences = getSharedPreferences("LOG_NUMBER", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        //initializing elements
        erase = findViewById(R.id.resetbtn);
        button_page = findViewById(R.id.showwbp);
        button = findViewById(R.id.extrabtn);
        final ProgressDialog progressDialog = new ProgressDialog(MainScreen.this);


        //starting progress dialog
        progressDialog.StartProgressDialog();


        //checking if log_num equal to 0
        if(sharedPreferences.getInt("log_num", 0 ) == 0) {

            //checking if answer is true or not
            if(getBoolAnswer() == "true" && getBoolAnswer() != null){
                openWebView();

            }else if (getBoolAnswer() == "false" && getBoolAnswer() != null){
                //if no -> starting MainActivity with game
                startActivity(new Intent(MainScreen.this, MainActivity.class));
            }else{
                //error showing toast
                Toast.makeText(MainScreen.this, "Error with answer", Toast.LENGTH_SHORT).show();
            }

        }
        editor.putInt("log_num",1);
        editor.apply();
        //setting handler to delay dialog dismissing
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismissDialog();
            }
        }, 2000);


        button_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebView();
                //buttons now is gone
                button_page.setVisibility(View.GONE);
                erase.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, MainActivity.class));

            }
        });

        //erase statistics button
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking if log_num = 1
                if(sharedPreferences.getInt("log_num", 0) == 1){
                    editor.putInt("log_num", 0);
                    editor.apply();
                    //set highscores to 0
                } else {
                    //Toast about error
                    Toast.makeText(MainScreen.this, "Error with erase button", Toast.LENGTH_SHORT).show();
                }
                MainGameView.highScore[0] = 0;
                MainGameView.highScore[1] = 0;
                MainGameView.highScore[2] = 0;
                MainGameView.highScore[3] = 0;
            }
        });


    }


    //getting answer from server
    public String getBoolAnswer(){
        final SharedPreferences sharedPreferences = getSharedPreferences("LOG_NUM", Context.MODE_PRIVATE);;
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Example> call = apiInterface.getResponse();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                //putting response flag
                editor.putString("api_response",  response.body().getResponse());
                editor.apply();

            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainScreen.this, "Error with loading json", Toast.LENGTH_SHORT).show();

            }
        });

        //returning this flag
        return sharedPreferences.getString("api_response","");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
}




           @SuppressLint("SetJavaScriptEnabled")
           public void openWebView(){
             //initializing webView
              webView = (WebView) findViewById(R.id.webview);

               //setting webview Client
               webView.setWebViewClient(new WebViewClient());

               //running webView
               webView.loadUrl("https://html5test.com/");
               WebSettings webSettings = webView.getSettings();

               //enabling javascript
               webSettings.setJavaScriptEnabled(true);

}

}