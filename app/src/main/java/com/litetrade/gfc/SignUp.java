package com.litetrade.gfc;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignUp extends AppCompatActivity {

    Calendar calender;
    DatePickerDialog datePicker;
    int year,month,dayofMonth;
    TextView dob;
    EditText fn,email,ph,pass,repass;
    Button submit;
    RadioButton m,f;
    String gen="";
    ProgressDialog progressDialog;
    int f1;
    String url1="https://hsgawb.com/LiteTrade/check_ph.php";
    List<List_Data_cp> list_data_cp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        list_data_cp=new ArrayList<>();

        getSupportActionBar().hide();

        //initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);

        dob=(TextView)findViewById(R.id.dob);
        fn=(EditText)findViewById(R.id.fn);
        email=(EditText)findViewById(R.id.email);
        ph=(EditText)findViewById(R.id.ph);
        pass=(EditText)findViewById(R.id.pass);
        repass=(EditText)findViewById(R.id.repass);
        submit=(Button)findViewById(R.id.submit);
        m=(RadioButton)findViewById(R.id.m);
        f=(RadioButton)findViewById(R.id.f);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (m.isChecked()){
                    gen="Male";
                }else if (f.isChecked()){
                    gen="Female";
                }
                if (fn.getText().toString().trim().equals("")){
                    fn.setError("Please Enter Your Name");
                    fn.requestFocus();
                }else if (email.getText().toString().trim().equals("")){
                    email.setError("Please Enter Your Email");
                    email.requestFocus();
                }else if (ph.getText().toString().trim().equals("")){
                    ph.setError("Please Enter Your Phone Number");
                    ph.requestFocus();
                }else if (dob.getText().toString().trim().equals("  Date of Birth")){
                    dob.setError("Please Enter Your Date of Birth");
                    dob.requestFocus();
                }else if (gen.equals("")){
                    Toast.makeText(SignUp.this, "Please Enter Your Gender", Toast.LENGTH_SHORT).show();
                }else if (pass.getText().toString().trim().equals("")){
                    pass.setError("Please Enter a Password");
                    pass.requestFocus();
                }else if (!repass.getText().toString().equals(pass.getText().toString())){
                    repass.setError("Password not matched!");
                    repass.requestFocus();
                }else {
                    progressDialog.show();
                    check_ph cp=new check_ph();
                    new Thread(cp).start();

                }
            }
        });



        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calender= Calendar.getInstance();
                year=calender.get(Calendar.YEAR);
                month=calender.get(Calendar.MONTH);
                dayofMonth=calender.get(Calendar.DAY_OF_MONTH);

                datePicker=new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayofMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,dayofMonth);
                datePicker.show();

            }
        });



    }
    class check_ph implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
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
                            String[] PhNo = new String[array.length()];
                            PhNo[i] = listDatacp.getPhone();

                            if (PhNo[i].equals(email.getText().toString().trim())) {
                                f1 = 1;
                            } else {
                                f1= 0;
                            }

                        }
                        if (f1 == 1) {
                            showDialog();
                            progressDialog.dismiss();
                        } else {
                            Intent i=new Intent(getApplicationContext(),email_verify.class);
                            i.putExtra("name",fn.getText().toString());
                            i.putExtra("email",email.getText().toString());
                            i.putExtra("phone",ph.getText().toString());
                            i.putExtra("dob",dob.getText().toString());
                            i.putExtra("pass",pass.getText().toString());
                            i.putExtra("gen",gen);
                            startActivity(i);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Intent i=new Intent(getApplicationContext(),email_verify.class);
                        i.putExtra("name",fn.getText().toString());
                        i.putExtra("email",email.getText().toString());
                        i.putExtra("phone",ph.getText().toString());
                        i.putExtra("dob",dob.getText().toString());
                        i.putExtra("pass",pass.getText().toString());
                        i.putExtra("gen",gen);
                        startActivity(i);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Some error occurred!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }}


    public void showDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Message");

        dialog.setMessage("This Email is already registered!");

        dialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {


                finish();

            }

        });

        dialog.setCancelable(true);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }

        }).show();
    }

        }
