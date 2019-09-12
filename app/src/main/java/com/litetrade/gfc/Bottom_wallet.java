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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bottom_wallet extends Fragment {
    Button addcash;
    TextView email1, bal1,win_bal;
    String url_wal = "https://hsgawb.com/LiteTrade/wallet.php";
    List<List_Data_sl1> list_data_sl1;
    String login_phone = "Null";
    String p;
    View view;
    List<List_Data_fd2> list_data_fd2;
    String url="https://hsgawb.com/LiteTrade/walletDetails.php";
    TextView depo,myearn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottom_wallet, container, false);

        SharedPreferences b=getActivity().getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        p=b.getString("loginPhone","No");

        addcash = (Button) view.findViewById(R.id.addcash);
        email1 = (TextView) view.findViewById(R.id.email1);
        bal1 = (TextView) view.findViewById(R.id.bal1);
        win_bal=(TextView)view.findViewById(R.id.win_bal1);
        depo=(TextView)view.findViewById(R.id.depo);
        myearn=(TextView)view.findViewById(R.id.myearn);


        list_data_fd2=new ArrayList<>();
        match_details md=new match_details();
        new Thread(md).start();

        addcash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Add_money.class);
                startActivity(i);
            }
        });

        return view;
    }

    class match_details implements Runnable{
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
                            List_Data_fd2 listDatafd2 = new List_Data_fd2(
                                    ob.getString("deposite"),
                                    ob.getString("extra_earn"),
                                    ob.getString("wining_blance"),
                                    ob.getString("wallet_balance"));
                            list_data_fd2.add(listDatafd2);

                            bal1.setText(listDatafd2.getTotalAmount());
                            depo.setText(listDatafd2.getDeposite());
                            win_bal.setText(listDatafd2.getWinAmount());
                            myearn.setText(listDatafd2.getExtraEarn());
                            /*wb.setText(listDatafd.getTotalAmount());
                            tb.setText(listDatafd.getWinAmount());
                            rt.setText(listDatafd.getRedChallenge());
                            gt.setText(listDatafd.getGreenChallenge());*/

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

