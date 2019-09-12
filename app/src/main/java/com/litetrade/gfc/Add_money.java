package com.litetrade.gfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_money extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = Razorpay.class.getSimpleName();
    int rupee;
    EditText money,email,phone;
    Button ok,one,two,three;
    String add_mon_Url="https://hsgawb.com/LiteTrade/add_money.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        Checkout.preload(getApplicationContext());
        money=(EditText)findViewById(R.id.money);
        ok=(Button)findViewById(R.id.ok);
       /*one=(Button)findViewById(R.id.one);
        two=(Button)findViewById(R.id.two);
        three=(Button)findViewById(R.id.three); */
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rupee= Integer.parseInt(money.getText().toString());
                startPayment();
            }
        });
       /* one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money.setText("100");
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money.setText("200");
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money.setText("300");
            }
        });*/
    }
    public void startPayment() {

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", rupee*100);

            JSONObject preFill = new JSONObject();

            options.put("prefill", preFill);
            preFill.put("email",email.getText().toString());
            preFill.put("contact",phone.getText().toString());


            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, email.getText().toString() + "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            amm_up au=new amm_up();
            new Thread(au).start();

                   } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    class amm_up implements Runnable{

        @Override
        public void run() {
            final String Email,Ammount;

            Email=email.getText().toString().trim();
            Ammount=money.getText().toString().trim();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, add_mon_Url, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Add_money.this, response, Toast.LENGTH_SHORT).show();
                            //progressDialog.hide();

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
                            Toast.makeText(Add_money.this, "Some Error Occurred!", Toast.LENGTH_SHORT).show();
                            //progressDialog.hide();
                        }
                    });
                    //error.printStackTrace();
                }
            })

            {
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String,String>();

                    params.put("Email",Email);
                    params.put("Number",Ammount);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
    }

}