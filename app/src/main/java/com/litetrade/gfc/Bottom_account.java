package com.litetrade.gfc;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class Bottom_account extends Fragment {

    View view;
    Button kyc,bankdetails;
    TextView name,email,phone,dob,gen;
    String url="https://hsgawb.com/LiteTrade/profile.php";
    List<List_Data_sl> list_data_sl;
    String login_phone="Null";
    String p;
    TextView idtype,idno;
    Button pic;
    ImageView proimg;
    TextView ahn,an,bn,ifs,phn;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view=inflater.inflate(R.layout.bottom_account,container,false);


        SharedPreferences b=getActivity().getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        p=b.getString("loginPhone","No");

        list_data_sl=new ArrayList<>();

        ahn=(TextView)view.findViewById(R.id.ahn);
        an=(TextView)view.findViewById(R.id.an);
        bn=(TextView)view.findViewById(R.id.bn);
        ifs=(TextView)view.findViewById(R.id.ifs);
        phn=(TextView)view.findViewById(R.id.phn);
       name=(TextView)view.findViewById(R.id.name);
       email=(TextView)view.findViewById(R.id.email);
       phone=(TextView)view.findViewById(R.id.phone);
       dob=(TextView)view.findViewById(R.id.dob);
       gen=(TextView)view.findViewById(R.id.gen);
       idtype=(TextView)view.findViewById(R.id.idtype);
       idno=(TextView)view.findViewById(R.id.idno);
       pic=(Button)view.findViewById(R.id.pic);
       proimg=(ImageView)view.findViewById(R.id.proimg);

       profile p=new profile();
       new Thread(p).start();


       pic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Intent i=new Intent(getContext(),img_upload.class);
              startActivity(i);
           }
       });




       kyc=(Button)view.findViewById(R.id.KYC); bankdetails=(Button)view.findViewById(R.id.bank_details);
       kyc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

              Intent intent=new Intent(getActivity(),KYC_update.class);
              startActivity(intent);

           }
       });

      bankdetails.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              Intent intent=new Intent(getActivity(),BankDetails_update.class);
              startActivity(intent);

          }
      });



        return view;
    }

    class profile implements Runnable{
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
                            List_Data_sl listDatasl = new List_Data_sl(ob.getString("name"),
                                    ob.getString("email"),
                                    ob.getString("phone"),
                                    ob.getString("dob"),
                                    ob.getString("gender"),
                                    ob.getString("id_name"),
                                    ob.getString("id_number"),
                                    ob.getString("profile_pic"),
                                    ob.getString("ifsc"),
                                    ob.getString("bank_name"),
                                    ob.getString("account_no"),
                                    ob.getString("acc_hld_name"),
                                    ob.getString("acc_ph_no"));
                            list_data_sl.add(listDatasl);

                            name.setText(listDatasl.getName());
                            email.setText(listDatasl.getEmail());
                            phone.setText(listDatasl.getPhone());
                            dob.setText(listDatasl.getDob());
                            gen.setText(listDatasl.getGender());
                            idtype.setText(listDatasl.getIdtype()+": ");
                            idno.setText(listDatasl.getIdno());
                            ifs.setText(listDatasl.getIfsc());
                            bn.setText(listDatasl.getBank_name());
                            an.setText(listDatasl.getAccount_no());
                            ahn.setText(listDatasl.getAcc_hld_name());
                            phn.setText(listDatasl.getAcc_ph_no());

                            if (!listDatasl.getImg().equals("null")){

                                 Glide.with(getContext()).load(listDatasl.getImg()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(proimg);

                            }



                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(),"Error Occurred!",Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),"Some error occurred!",Toast.LENGTH_SHORT).show();
                    //loader.hide();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", p);


                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }

    }


}
