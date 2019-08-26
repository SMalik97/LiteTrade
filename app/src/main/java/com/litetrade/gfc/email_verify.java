package com.litetrade.gfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class email_verify extends AppCompatActivity {

    TextView uemail;
    EditText upass;
    Button submit;
    private FirebaseAuth mAuth;
    String url="https://hsgawb.com/LiteTrade/reg_user.php";
    String name,ph,emailid,dob,pass,gen,ref;
    ProgressDialog progressDialog;
    String login_status="No";
    String login_phone="Null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);
        uemail = (TextView) findViewById(R.id.uemail);
        upass = (EditText) findViewById(R.id.upass);
        submit = (Button) findViewById(R.id.submit);

        mAuth = FirebaseAuth.getInstance();
        //initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);


        name=getIntent().getExtras().getString("name");
        ph=getIntent().getExtras().getString("phone");
        emailid=getIntent().getExtras().getString("email");
        dob=getIntent().getExtras().getString("dob");
        pass=getIntent().getExtras().getString("pass");
        gen=getIntent().getExtras().getString("gen");
        ref=getIntent().getExtras().getString("ref");


        uemail.setText("To verify that "+emailid+" is your gmail id you have to submit your gmail password");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upass.getText().toString().trim().equals("")) {
                    upass.setError("Please Enter Your Email Password");
                    upass.requestFocus();
                } else {
                    progressDialog.show();
                    registerUser();
                }
            }
        });

    }



        private void registerUser() {
            String email = emailid;
            String password = upass.getText().toString();


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        reg r=new reg();
                        new Thread(r).start();
                    } else {

                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                            reg r=new reg();
                            new Thread(r).start();
                            //progressDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                }
            });
        }



    class reg implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), "Registered Successful!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            SharedPreferences sp = getSharedPreferences(login_status, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("loginStatus", "Yes");
                            editor.apply();

                            SharedPreferences sp2 = getSharedPreferences(login_phone, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sp2.edit();
                            editor2.putString("loginPhone", emailid);
                            editor2.apply();


                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Some error occurred!", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();

                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("phone", ph);
                    params.put("email", emailid);
                    params.put("dob", dob);
                    params.put("pass", convertPassMD5(pass));
                    params.put("gen", gen);
                    params.put("ref",ref);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }}


        public static String convertPassMD5(String pass){
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

}
