package com.litetrade.gfc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Bottom_wallet extends Fragment {
    Button addcash;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view=inflater.inflate(R.layout.bottom_wallet,container,false);

       addcash=(Button)view.findViewById(R.id.addcash);

       addcash.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(getContext(),Add_money.class);
               startActivity(i);
           }
       });

        return view;
    }
}
