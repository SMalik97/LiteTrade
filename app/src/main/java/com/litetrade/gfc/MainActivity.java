package com.litetrade.gfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    List<List_Data_cp> list_data_cp;
    List<List_Data_lu> list_data_lu;
    Button signUp,logIn;
    ProgressDialog progressDialog;
    String login_status="No";
    String login_phone="Null";
    EditText email,Password;
    int f;
    String url="https://hsgawb.com/LiteTrade/check_ph.php";
    String url2="https://hsgawb.com/LiteTrade/login_user.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(true);

        list_data_cp=new ArrayList<>();
        list_data_lu=new ArrayList<>();

        email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.Password);

        signUp=(Button)findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),SignUp.class);
                startActivity(i);
            }
        });

        logIn=(Button)findViewById(R.id.LogIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().trim().equals("")) {
                    email.setError("Please Enter Your Email");
                    email.requestFocus();
                } else if (Password.getText().toString().equals("")) {
                    Password.setError("Please Enter Your Password");
                    Password.requestFocus();
                } else {
                    progressDialog.show();
                    login l = new login();
                    new Thread(l).start();
                }


            }
        });



    }


    class login implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject ob = array.getJSONObject(i);
                            List_Data_cp listDatacp = new List_Data_cp(
                                    ob.getString("email"));
                            list_data_cp.add(listDatacp);
                            String[] PhNo=new String[array.length()];
                            PhNo[i]=listDatacp.getPhone();

                            if (PhNo[i].equals(email.getText().toString())){
                                f=1;
                            }else {
                                f=0;
                            }

                        }
                        if (f==1){
                            fetch_pass fp=new fetch_pass();
                            new Thread(fp).start();
                        }else {
                            Toast.makeText(getApplicationContext(), "This email is not registered!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Some.... error occurred!", Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Some error occurred!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            });
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        }


        class fetch_pass implements Runnable{
            @Override
            public void run() {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject ob = array.getJSONObject(i);
                                List_Data_lu listDatalu = new List_Data_lu(
                                        ob.getString("email"),
                                        ob.getString("password"));
                                list_data_lu.add(listDatalu);
                                String[] password=new String[array.length()];
                                password[i]=listDatalu.getPass();

                                if (password[i].equals(convertPassMD5(Password.getText().toString()))){
                                   Intent j=new Intent(getApplicationContext(),LoggedPage.class);
                                   startActivity(j);

                                    SharedPreferences sp = getSharedPreferences(login_status, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("loginStatus", "Yes");
                                    editor.apply();

                                    SharedPreferences sp2 = getSharedPreferences(login_phone, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor2 = sp2.edit();
                                    editor2.putString("loginPhone", email.getText().toString().trim());
                                    editor2.apply();

                                    finish();

                                }else {
                                    Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Some error occurred!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Some error occurred!",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("email",email.getText().toString().trim());

                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


            }

        }
        public String convertPassMD5(String pass){
            String password=null;
            MessageDigest mdEnc;
            try{
                mdEnc=MessageDigest.getInstance("MD5");
                mdEnc.update(pass.getBytes(),0,pass.length());
                pass=new BigInteger(1,mdEnc.digest()).toString(16);
                while (pass.length()<32){
                    pass="0" + pass;
                }
                password=pass;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return password;
        }



    }}
