package com.litetrade.gfc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bottom_myMatch extends Fragment {

    EditText re,ge;
    TextView rt,gt,tb,wb,noti;
    Button submit;
    View view;
    String url="https://hsgawb.com/LiteTrade/challenge.php";
    String url2="https://hsgawb.com/LiteTrade/challenge_details.php";
    String login_status="No";
    String login_phone="Null";
    List<List_Data_fd> list_data_fd;
    String p,ei;
    int ftb;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.activity_bottom_mymatch,null);

        re=(EditText)view.findViewById(R.id.re);
        ge=(EditText)view.findViewById(R.id.ge);
        rt=(TextView) view.findViewById(R.id.rt);
        gt=(TextView) view.findViewById(R.id.gt);
        tb=(TextView) view.findViewById(R.id.tb);
        wb=(TextView) view.findViewById(R.id.wb);
        noti=(TextView) view.findViewById(R.id.noti);



        list_data_fd=new ArrayList<>();

        noti.setVisibility(View.INVISIBLE);

        re.setVisibility(View.INVISIBLE);
        ge.setVisibility(View.INVISIBLE);


        getemail();
        ei=p+"";



        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re.setVisibility(View.VISIBLE);
                rt.setVisibility(View.INVISIBLE);
            }
        });



        gt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.setVisibility(View.INVISIBLE);
                ge.setVisibility(View.VISIBLE);
            }
        });







        submit=(Button)view.findViewById(R.id.submit);



        match_details md=new match_details();
        new Thread(md).start();




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double total_balance=Double.parseDouble(tb.getText().toString());
                int a=Integer.parseInt(re.getText().toString());
                int b=Integer.parseInt(ge.getText().toString());

                if (total_balance >= (a+b)) {

                    rt.setVisibility(View.VISIBLE);
                    gt.setVisibility(View.VISIBLE);
                    re.setVisibility(View.INVISIBLE);
                    ge.setVisibility(View.INVISIBLE);
                   /* rt.setText("$ " + re.getText().toString());
                    gt.setText("$ " + ge.getText().toString());

                    double total_balance2=Double.parseDouble(tb.getText().toString());
                    int a2=Integer.parseInt(re.getText().toString());
                    int b2=Integer.parseInt(ge.getText().toString());
                    int aa=a2+b2;
                    //tb.setText(total_balance2-aa);

*/                  int a2=Integer.parseInt(re.getText().toString());
                    int b2=Integer.parseInt(ge.getText().toString());
                   // Toast.makeText(getContext(), a2+"   "+b2, Toast.LENGTH_LONG).show();
                    int tb2=Integer.parseInt(tb.getText().toString());
                    ftb=tb2-(a2+b2);
                    tb.setText(ftb+"");

                    getemail();
                    challenge c = new challenge();
                    new Thread(c).start();
                }else {
                    noti.setVisibility(View.VISIBLE);
                    noti.setText("You have not sufficient balance!");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            noti.setVisibility(View.INVISIBLE);


                        }
                    },2000);
                }
            }
        });



        return view;
    }

    class challenge implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(), response+"", Toast.LENGTH_LONG).show();
                            noti.setVisibility(View.VISIBLE);
                            noti.setText("Submit Successful!");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    noti.setVisibility(View.INVISIBLE);


                                }
                            },2000);


                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    noti.setVisibility(View.VISIBLE);
                    noti.setText("Some error occurred!");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            noti.setVisibility(View.INVISIBLE);


                        }
                    },2000);
                    error.printStackTrace();

                    //progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rc", re.getText().toString());
                    params.put("gc", ge.getText().toString());
                    params.put("tb", tb.getText().toString());
                    params.put("wb", wb.getText().toString());
                    params.put("email", p);


                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }
    }

    public void getemail(){
        SharedPreferences b=getActivity().getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        p=b.getString("loginPhone","No");
    }


    class match_details implements Runnable{
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
                            List_Data_fd listDatafd = new List_Data_fd(
                                    ob.getString("red_challenge"),
                                    ob.getString("green_challenge"),
                                    ob.getString("wallet_balance"),
                                    ob.getString("wining_blance"));
                            list_data_fd.add(listDatafd);

                            wb.setText(listDatafd.getTotalAmount());
                            tb.setText(listDatafd.getWinAmount());
                            rt.setText(listDatafd.getRedChallenge());
                            gt.setText(listDatafd.getGreenChallenge());

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error Occurred!"+response, Toast.LENGTH_LONG).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),"Some error occurred!",Toast.LENGTH_SHORT).show();
                   // progressDialog.dismiss();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("email",p);

                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);


        }

    }

}
