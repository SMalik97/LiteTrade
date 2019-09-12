package com.litetrade.gfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class LiteTradeSplash extends AppCompatActivity {
    String login_status="No";
    String login_phone="Null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_trade_splash);

        getSupportActionBar().hide();


        SharedPreferences b=getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        String p=b.getString("loginPhone","No");


        SharedPreferences a=getSharedPreferences(login_status, Context.MODE_PRIVATE);
        String v=a.getString("loginStatus","No");


        if (v.equals("No")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();


                }
            },4000);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i=new Intent(getApplicationContext(),LoggedPage.class);
                    startActivity(i);
                    finish();


                }
            },4000);
        }


    }

}
