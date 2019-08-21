package com.litetrade.gfc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class img_upload extends AppCompatActivity {
    final int CODE_GALLERY_REQUEST=999;
    private Bitmap bitmap;
    ProgressDialog progressDialog;
    String login_phone="Null";
    String p;
    String uploadUrl="https://hsgawb.com/LiteTrade/picUpload.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_upload);

        SharedPreferences b=getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        p=b.getString("loginPhone","No");

        //initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(true);


        ActivityCompat.requestPermissions(img_upload.this,new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                CODE_GALLERY_REQUEST

        );
    }

    //select image from gallary.........................................................
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==CODE_GALLERY_REQUEST && resultCode==RESULT_OK && data !=null)
        {
            Uri filepath=data.getData();

            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                //we can set this bitmap image in a imageview
                //imageView.setImageBitmap(bitmap);
                //imgView.setVisibility(View.VISIBLE);
                //NAME.setVisibility(View.VISIBLE);





//upload image to server ...............................................................................

                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(img_upload.this, response+"", Toast.LENGTH_SHORT).show();

                        progressDialog.hide();
                        finish();
                       // String u="https://hsgawb.com/android/uploads/"+sp+".png";
                       // Glide.with(my_profile.this).load(u).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(pic);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(img_upload.this, error.toString(), Toast.LENGTH_SHORT).show();
                        //String u="https://hsgawb.com/android/uploads/"+sp+".png";
                        //Glide.with(my_profile.this).load(u).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(pic);
                        progressDialog.hide();
                        finish();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params=new HashMap<>();

                        String imageData=imageToString(bitmap);
                        params.put("image", imageData);
                        params.put("name",p);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(img_upload.this);
                requestQueue.add(stringRequest);









            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imagebytes= outputStream.toByteArray();
        //return Base64.encodeToString(imagebytes,Base64.DEFAULT);

        String encodedImage= Base64.encodeToString(imagebytes,Base64.DEFAULT);
        return  encodedImage;
    }



}
