package com.litetrade.gfc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BankDetails_update extends AppCompatActivity {

    Button sub_bank;
    //TextView pic1;
    String uploadUrl="https://hsgawb.com/LiteTrade/bank_up.php";
    EditText ifsc,b_name,ac_no,c_ac_no,acc_name,acc_ph;
    String login_phone="Null";
    String p;
    ProgressDialog progressDialog;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bankdetails_update);


        getSupportActionBar().hide();

        //initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(true);


        ifsc=(EditText)findViewById(R.id.ifsc);
        b_name=(EditText)findViewById(R.id.b_name);
        ac_no=(EditText)findViewById(R.id.ac_no);
        c_ac_no=(EditText)findViewById(R.id.c_ac_no);
        acc_name=(EditText)findViewById(R.id.acc_name);
        acc_ph=(EditText)findViewById(R.id.acc_ph);
        sub_bank=(Button)findViewById(R.id.sub_bank);


        SharedPreferences b=getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        p=b.getString("loginPhone","No");



        sub_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if  ( ifsc.getText().toString().equals("")){
                    ifsc.setError("IFSC number can't be empty!");
                    ifsc.requestFocus();
                }else if (b_name.getText().toString().equals("")){
                    b_name.setError("Bank Name can't be empty!");
                    b_name.requestFocus();
                }else if (ac_no.getText().toString().equals("")){
                    ac_no.setError("Account Number can't be empty!");
                    ac_no.requestFocus();
                }else if (!ac_no.getText().toString().equals(c_ac_no.getText().toString())){
                    c_ac_no.setError("Account Number not matched!");
                    c_ac_no.requestFocus();
                }else if (acc_name.getText().toString().equals("")){
                    acc_name.setError("Account Holder Name can't be empty!");
                    acc_name.requestFocus();
                }else if (acc_ph.getText().toString().equals("")){
                    acc_ph.setError("Phone Number can't be empty!");
                    acc_ph.requestFocus();
                }else {
                     progressDialog.show();
                    bank_detiles bd=new bank_detiles();
                    new Thread(bd).start();
                }

            }
        });
    }
    class bank_detiles implements Runnable{

        @Override
        public void run() {
            final String Ifsc,B_name ,Ac_no,Acc_name,Acc_ph;
            Ifsc=ifsc.getText().toString().trim();
            B_name=b_name.getText().toString().trim();
            Ac_no=ac_no.getText().toString().trim();
            Acc_name=acc_name.getText().toString().trim();
            Acc_ph=acc_ph.getText().toString().trim();


            StringRequest stringRequest=new StringRequest(Request.Method.POST, uploadUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BankDetails_update.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BankDetails_update.this, "Some Error Occurred!", Toast.LENGTH_SHORT).show();
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

                    params.put("IFSC",Ifsc);
                    params.put("B_Name",B_name);
                    params.put("AC_No",Ac_no);
                    params.put("Acc_Name",Acc_name);
                    params.put("Acc_Ph",Acc_ph);
                    params.put("Email",p);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
    }

}
