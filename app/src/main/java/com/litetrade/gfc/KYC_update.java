package com.litetrade.gfc;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KYC_update extends AppCompatActivity {

    ProgressDialog progressDialog;
    Button kyc;
    TextView pic1;
    String uploadUrl="https://hsgawb.com/LiteTrade/kyc.php";
    EditText num_kyc,email_kyc;
    Spinner spinner;
    List<String> list;
    ArrayAdapter<String> SpinnerAdapter;
    String login_phone="Null";
    String p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_update);
        spinner=(Spinner)findViewById(R.id.spinner);

        //initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(true);


        num_kyc=(EditText)findViewById(R.id.num_kyc);
        pic1=(TextView) findViewById(R.id.pic1);

        kyc=(Button)findViewById(R.id.kyc);
        getSupportActionBar().hide();


        SharedPreferences b=getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        p=b.getString("loginPhone","No");



        list=new ArrayList<String>();
        list.add("Select");
        list.add("Voter Id");
        list.add("Aadhar Card");
        list.add("PAN Card");
        SpinnerAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list)
        {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#ffffff"));
                return v;
            }
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,
                        parent);
                v.setBackgroundColor(Color.parseColor("#000000"));
                ((TextView) v).setTextColor(Color.parseColor("#ffffff"));
                return v;
            }
        };

        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(SpinnerAdapter); // Set Adapter in the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String state = parentView.getItemAtPosition(position).toString(); // selected item in the list

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }
        });

        kyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if  (num_kyc.getText().toString().equals("")){
                    num_kyc.setError("ID number can't be empty!");
                    num_kyc.requestFocus();
                }else if (spinner.getSelectedItem().toString().equals("Select")){
                    Toast.makeText(KYC_update.this, "Select your Card", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();
                    kycup ku=new kycup();
                    new Thread(ku).start();
                }
            }
        });

    }

    class kycup implements Runnable{

        @Override
        public void run() {
            final String Email,Number,Id;
            Id=spinner.getSelectedItem().toString().trim();
            Email=p;
            Number=num_kyc.getText().toString().trim();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, uploadUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(KYC_update.this, response, Toast.LENGTH_SHORT).show();
                            progressDialog.hide();

                            finish();
                        }
                    });


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    runOnUiThread(new Runnable() {
                        @Override

                        public void run() {
                            Toast.makeText(KYC_update.this, "Some Error Occurred!", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    });
                    //error.printStackTrace();
                }
            })

            {
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String,String>();
                    params.put("ID",Id);
                    params.put("Email",Email);
                    params.put("Number",Number);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
    }

}
