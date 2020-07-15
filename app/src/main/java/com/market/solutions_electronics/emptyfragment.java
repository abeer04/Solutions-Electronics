package com.market.solutions_electronics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class emptyfragment extends Fragment implements View.OnClickListener {


    public emptyfragment() {
        // Required empty public constructor
    }
        Button checkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_emptyfragment, container, false);

        checkout=view.findViewById(R.id.checkout);
        checkout.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.checkout) {
            Toast.makeText(getActivity(), "No Item in Cart",
                    Toast.LENGTH_LONG).show();

        }

    }
}
